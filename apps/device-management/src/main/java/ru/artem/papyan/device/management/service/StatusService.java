package ru.artem.papyan.device.management.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.artem.papyan.device.management.dto.CreateSensorRequest;
import ru.artem.papyan.device.management.dto.DeleteSensorRequest;
import ru.artem.papyan.device.management.dto.Sensor;
import ru.artem.papyan.device.management.dto.UpdateSensorRequest;
import ru.artem.papyan.smarthome.dto.MetricDto;
import ru.artem.papyan.device.management.dto.event.StatusMessage;
import ru.artem.papyan.device.management.dto.event.UpdatePayload;
import ru.artem.papyan.device.management.entity.DeviceEntity;
import ru.artem.papyan.device.management.entity.DeviceStatus;
import ru.artem.papyan.device.management.integration.SmartHomeClient;
import ru.artem.papyan.device.management.repository.DeviceRepository;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusService {

    private final SmartHomeClient smartHomeClient;
    private final DeviceRepository deviceRepository;
    private final MetricService metricService;
    private final ObjectMapper objectMapper;

    public void processMessage(StatusMessage message) {
        String command = message.status();
        Map<String, ?> payload = message.payload();

        switch (command) {
            case "create" -> handleCreateCommand(payload);
            case "update" -> handleUpdateCommand(payload);
            case "delete" -> handleDeleteCommand(payload);
            default -> log.warn("Unknown command received: {}", command);
        }
    }

    private void handleCreateCommand(Map<String, ?> payload) {
        try {
            // Convert payload to CreateSensorRequest
            CreateSensorRequest createRequest = objectMapper.convertValue(payload, CreateSensorRequest.class);

            // Call legacy system to create sensor
            Sensor sensorResponse = smartHomeClient.createSensor(createRequest);

            // Create and save device entity with legacy system ID
            DeviceEntity device = new DeviceEntity();
            device.setName(createRequest.name());
            device.setType(createRequest.type());
            device.setLocation(createRequest.location());
            device.setDeviceStatus(DeviceStatus.ACTIVATED);
            device.setCreatedAt(OffsetDateTime.now());
            device.setLastUpdated(OffsetDateTime.now());
            device.setLegacySystemId(sensorResponse.id());

            // Save the device entity
            DeviceEntity savedDevice = deviceRepository.save(device);

            log.info("Successfully created device with ID {} and legacy system ID {}",
                    savedDevice.getId(), sensorResponse.id());

        } catch (Exception e) {
            log.error("Failed to process create command with payload: {}", payload, e);
        }
    }

    private DeviceEntity findDeviceByIdOrLegacyId(Long id, Long legacyId) {
        if (id != null) {
            return deviceRepository.findById(id).orElse(null);
        } else if (legacyId != null) {
            return deviceRepository.findByLegacySystemId(legacyId).orElse(null);
        }
        return null;
    }

    private void sendMetricToQueue(Long deviceId, UpdateSensorRequest updateData) {
        MetricDto metricDto = new MetricDto(
                deviceId,
                updateData.unit(),
                Optional.ofNullable(updateData.value()).orElse(0D).toString(),
                OffsetDateTime.now()
        );

        metricService.send(metricDto);
    }

    private void handleUpdateCommand(Map<String, ?> payload) {
        try {
            log.info("Update command received. Payload: {}", payload);

            // Convert payload to UpdatePayload
            UpdatePayload updatePayload = objectMapper.convertValue(payload, UpdatePayload.class);

            // Find device entity by ID or legacy ID
            DeviceEntity device = findDeviceByIdOrLegacyId(updatePayload.id(), updatePayload.legacyId());
            if (device == null) {
                log.warn("Device not found for update. ID: {}, Legacy ID: {}", updatePayload.id(), updatePayload.legacyId());
                return;
            }

            // Call legacy system to update sensor
            Sensor sensorResponse = smartHomeClient.updateSensor(device.getLegacySystemId(), updatePayload.data());

            // Update device entity
            device.setName(updatePayload.data().name());
            device.setType(updatePayload.data().type());
            device.setLocation(updatePayload.data().location());
            device.setLastUpdated(OffsetDateTime.now());

            // Save the updated device entity
            DeviceEntity updatedDevice = deviceRepository.save(device);

            log.info("Successfully updated device with ID {} and legacy system ID {}",
                    updatedDevice.getId(), sensorResponse.id());

            // Check if we need to send metrics (unit and value are present)
            if (updatePayload.data().unit() != null && updatePayload.data().value() != null) {
                sendMetricToQueue(updatedDevice.getId(), updatePayload.data());
            }

        } catch (Exception e) {
            log.error("Failed to process update command with payload: {}", payload, e);
        }
    }

    private void handleDeleteCommand(Map<String, ?> payload) {
        try {
            log.info("Delete command received. Payload: {}", payload);

            // Convert payload to DeleteSensorRequest
            DeleteSensorRequest deleteRequest = objectMapper.convertValue(payload, DeleteSensorRequest.class);

            // Find device entity by ID or legacy ID
            DeviceEntity device = findDeviceByIdOrLegacyId(deleteRequest.id(), deleteRequest.legacySystemId());
            if (device == null) {
                log.warn("Device not found for deletion. ID: {}, Legacy ID: {}", deleteRequest.id(), deleteRequest.legacySystemId());
                return;
            }

            // Call legacy system to delete sensor
            smartHomeClient.deleteSensor(device.getLegacySystemId());

            // Delete device entity
            deviceRepository.delete(device);

            log.info("Successfully deleted device with ID {} and legacy system ID {}",
                    device.getId(), device.getLegacySystemId());

        } catch (Exception e) {
            log.error("Failed to process delete command with payload: {}", payload, e);
        }
    }
}
