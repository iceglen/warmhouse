package ru.artem.papyan.smarthome.dto;

import java.time.OffsetDateTime;

public record MetricDto(
        long deviceId,
        String metricName,
        String metricValue,
        OffsetDateTime timestamp
) {
}
