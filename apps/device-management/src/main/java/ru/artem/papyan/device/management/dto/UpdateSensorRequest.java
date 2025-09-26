package ru.artem.papyan.device.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateSensorRequest(
        String name,
        String type,
        String location,
        Double value,
        String unit,
        String status
) {
}
