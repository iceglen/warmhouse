package ru.artem.papyan.device.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Sensor(
        Long id,
        String name,
        String type,
        String location,
        Double value,
        String status,
        OffsetDateTime lastUpdated,
        OffsetDateTime createdAt
) {
}
