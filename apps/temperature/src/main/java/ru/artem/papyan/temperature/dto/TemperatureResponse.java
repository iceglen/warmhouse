package ru.artem.papyan.temperature.dto;

import java.time.OffsetDateTime;

public record TemperatureResponse(
        double value,
        String unit,
        OffsetDateTime timestamp,
        String location,
        String status,
        String sensorId,
        String sensorType,
        String description
) {
}

