package ru.artem.papyan.device.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeleteSensorRequest(
        Long id,
        Long legacySystemId
) {
}
