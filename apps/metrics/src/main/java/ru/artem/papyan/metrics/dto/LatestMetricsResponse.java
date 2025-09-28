package ru.artem.papyan.metrics.dto;

import java.time.OffsetDateTime;

public record LatestMetricsResponse(
        String metricName,
        String metricValue,
        OffsetDateTime timestamp
) {
}