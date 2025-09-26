package ru.artem.papyan.metrics.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

    @Test
    void testMetricsQueueCreation() {
        RabbitMQConfig config = new RabbitMQConfig();
        Queue queue = config.metricsQueue();
        
        assertNotNull(queue);
        assertEquals(RabbitMQConfig.METRICS_QUEUE, queue.getName());
        assertTrue(queue.isDurable());
    }
}
