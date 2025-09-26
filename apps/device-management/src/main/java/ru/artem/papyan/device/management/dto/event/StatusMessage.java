package ru.artem.papyan.device.management.dto.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StatusMessage(
        String status,
        Map<String, ?> payload
) {
}
