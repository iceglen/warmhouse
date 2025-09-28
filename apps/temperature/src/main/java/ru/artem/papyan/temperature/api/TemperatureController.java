package ru.artem.papyan.temperature.api;

import org.springframework.web.bind.annotation.*;
import ru.artem.papyan.temperature.dto.TemperatureResponse;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@RequestMapping("/temperature")
public class TemperatureController {

    private final Logger log = Logger.getLogger(TemperatureController.class.getSimpleName());
    private final Random random = new Random();

    @GetMapping("/{sensorId}")
    public TemperatureResponse fetchTemperatureBySensorId(
            @PathVariable("sensorId") String sensorId
    ) {
        log.info(String.format("fetching temperature by sensorId: %s", sensorId));
        return this.composeResponse(sensorId, null);
    }

    @GetMapping
    public TemperatureResponse fetchTemperatureByLocation(
            @RequestParam("location") String location
    ) {
        log.info(String.format("fetching temperature by location: %s", location));
        return this.composeResponse(null, location);
    }

    private TemperatureResponse composeResponse(String sensorId, String location) {
        String generatedLocation = this.generateLocation(sensorId);
        String generatedSensorId = this.generateSensorId(location);

        return new TemperatureResponse(
                random.nextDouble(200D),
                "C",
                OffsetDateTime.now(ZoneId.of("Europe/Moscow")),
                generatedLocation,
                "active",
                generatedSensorId,
                String.format("sensor_%s_type", generatedSensorId),
                String.format("sensor_%s_description", generatedSensorId)
        );
    }

    private String generateLocation(String sensorId) {
        String defaultValue = "Unknown";

        if (sensorId == null) {
            return defaultValue;
        }

        return switch (sensorId) {
            case "1" -> "Living Room";
            case "2" -> "Bedroom";
            case "3" -> "Kitchen";
            default -> defaultValue;
        };
    }

    private String generateSensorId(String location) {
        String defaultValue = "0";

        if (location == null) {
            return defaultValue;
        }

        return switch (location) {
            case "Living Room" -> "1";
            case "Bedroom" -> "2";
            case "Kitchen" -> "3";
            default -> defaultValue;
        };
    }
}
