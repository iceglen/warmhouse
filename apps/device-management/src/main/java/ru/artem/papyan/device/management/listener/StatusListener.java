package ru.artem.papyan.device.management.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.artem.papyan.device.management.dto.event.StatusMessage;
import ru.artem.papyan.device.management.service.StatusService;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatusListener {

    private final ObjectMapper mapper;
    private final StatusService service;

    public void processMessage(Map<String, ?> data) {
        StatusMessage message = mapper.convertValue(data, StatusMessage.class);
        log.info("status data received: {}", data);

        service.processMessage(message);
    }
}
