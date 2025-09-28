package ru.artem.papyan.device.management.dto.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.artem.papyan.device.management.dto.UpdateSensorRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdatePayload(
        Long id,
        Long legacyId,
        UpdateSensorRequest data
) {
}
