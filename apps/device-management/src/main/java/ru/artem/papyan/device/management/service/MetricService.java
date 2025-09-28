package ru.artem.papyan.device.management.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.artem.papyan.device.management.config.RabbitMQConfig;
import ru.artem.papyan.smarthome.dto.MetricDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricService {

    private final RabbitTemplate rabbitTemplate;

    public void send(MetricDto data) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.METRICS_QUEUE, data);
            log.info("Metric sent to RabbitMQ: {}", data);
        } catch (Exception e) {
            log.error("Failed to send metric to RabbitMQ: {}", data, e);
        }
    }

}
