package ru.artem.papyan.smarthome.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MetricDto(
        long deviceId,
        String metricName,
        String metricValue,
        OffsetDateTime timestamp
) {
}
