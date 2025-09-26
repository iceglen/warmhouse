package ru.artem.papyan.metrics.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.artem.papyan.metrics.dto.LatestMetricsResponse;
import ru.artem.papyan.metrics.service.MetricsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping("/{deviceId}/latest")
    public List<LatestMetricsResponse> getLatestMetrics(@PathVariable Long deviceId) {
        return metricsService.getLatestMetricsByDeviceId(deviceId);
    }
}
