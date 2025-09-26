package ru.artem.papyan.device.management.scheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.artem.papyan.device.management.dto.Sensor;
import ru.artem.papyan.device.management.dto.UpdateSensorRequest;
import ru.artem.papyan.device.management.dto.event.StatusMessage;
import ru.artem.papyan.device.management.dto.event.UpdatePayload;
import ru.artem.papyan.device.management.integration.SmartHomeClient;
import ru.artem.papyan.device.management.service.StatusService;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmartHomeScheduler {

    private final SmartHomeClient smartHomeClient;
    private final StatusService statusService;

    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void scheduledTask() {
        List<Sensor> sensors = smartHomeClient.fetchSensors();

        log.info("fetched sensors: {}", sensors);

        if (sensors == null || sensors.isEmpty()) {
            return;
        }

        for (Sensor sensor : sensors) {
            UpdateSensorRequest updateRequest = new UpdateSensorRequest(
                    sensor.name(),
                    sensor.type(),
                    sensor.location(),
                    sensor.value(),
                    "temperature", // Default unit since legacy system doesn't provide it
                    sensor.status()
            );

            // Create UpdatePayload
            UpdatePayload payload = new UpdatePayload(
                    null,
                    sensor.id(), // Using sensor id as legacyId
                    updateRequest
            );
            
            Map<String, ?> converted = objectMapper.convertValue(payload, Map.class);

            statusService.processMessage(new StatusMessage("update", converted));
        }
    }
}