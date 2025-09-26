package ru.artem.papyan.metrics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.artem.papyan.smarthome.dto.MetricDto;
import ru.artem.papyan.metrics.service.MetricsService;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MetricsListener {

    private final ObjectMapper mapper;
    private final MetricsService service;

    public void processMetric(Map<String, ?> data) {
        MetricDto dto = mapper.convertValue(data, MetricDto.class);
        log.info("metric received: {}", dto);

        service.persistMetric(dto);
    }

    public void processMetric(MetricDto data) {
        log.info("metric received: {}", data);

        service.persistMetric(data);
    }
}
