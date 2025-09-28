package ru.artem.papyan.device.management.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.artem.papyan.device.management.dto.CreateSensorRequest;
import ru.artem.papyan.device.management.dto.MessageResponse;
import ru.artem.papyan.device.management.dto.Sensor;
import ru.artem.papyan.device.management.dto.UpdateSensorRequest;

import java.util.List;

@FeignClient(name = "smart-home-service", url = "${client.smart-home-service.path}")
public interface SmartHomeClient {

    @GetMapping("/api/v1/sensors")
    List<Sensor> fetchSensors();

    @PostMapping("/api/v1/sensors")
    Sensor createSensor(CreateSensorRequest request);

    @PutMapping("/api/v1/sensors/{sensorId}")
    Sensor updateSensor(@PathVariable Long sensorId, @RequestBody UpdateSensorRequest request);

    @DeleteMapping("/api/v1/sensors/{sensorId}")
    MessageResponse deleteSensor(@PathVariable Long sensorId);
}