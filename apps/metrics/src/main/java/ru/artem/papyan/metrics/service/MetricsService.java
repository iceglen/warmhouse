package ru.artem.papyan.metrics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artem.papyan.metrics.config.RabbitMQConfig;
import ru.artem.papyan.smarthome.dto.MetricDto;
import ru.artem.papyan.metrics.entity.Metric;
import ru.artem.papyan.metrics.entity.MetricUnit;
import ru.artem.papyan.metrics.repository.MetricRepository;
import ru.artem.papyan.metrics.repository.MetricUnitRepository;
import ru.artem.papyan.metrics.dto.LatestMetricsResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricsService {

    private final RabbitTemplate rabbitTemplate;
    private final MetricRepository metricRepository;
    private final MetricUnitRepository metricUnitRepository;

    public void sendMetric(String metricData) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.METRICS_QUEUE, metricData);
            log.info("Metric sent to RabbitMQ: {}", metricData);
        } catch (Exception e) {
            log.error("Failed to send metric to RabbitMQ: {}", metricData, e);
        }
    }

    @Transactional
    public void persistMetric(MetricDto metricDto) {
        // Find existing metric or create new one
        Metric metric = metricRepository.findByDeviceIdAndName(metricDto.deviceId(), metricDto.metricName())
                .orElseGet(() -> {
                    Metric newMetric = new Metric();
                    newMetric.setDeviceId(metricDto.deviceId());
                    newMetric.setName(metricDto.metricName());
                    return metricRepository.save(newMetric);
                });

        // Create and save metric unit
        MetricUnit metricUnit = new MetricUnit();
        metricUnit.setMetric(metric);
        metricUnit.setMetricValue(metricDto.metricValue());
        metricUnit.setCreatedAt(metricDto.timestamp());
        
        metricUnitRepository.save(metricUnit);
        
        log.info("metric persisted: deviceId={}, metricName={}, value={}",
                metricDto.deviceId(), metricDto.metricName(), metricDto.metricValue());
    }

    public List<LatestMetricsResponse> getLatestMetricsByDeviceId(Long deviceId) {
        List<MetricUnit> latestMetricUnits = metricUnitRepository.findLatestMetricsByDeviceId(deviceId);
        
        return latestMetricUnits.stream()
                .map(metricUnit -> new LatestMetricsResponse(
                        metricUnit.getMetric().getName(),
                        metricUnit.getMetricValue(),
                        metricUnit.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
