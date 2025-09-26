package ru.artem.papyan.device.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateSensorRequest(
        String name,
        String type,
        String location,
        String unit
) {
}
