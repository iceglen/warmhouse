package ru.artem.papyan.device.management.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.artem.papyan.device.management.listener.StatusListener;

@Configuration
public class RabbitMQConfig {

    public static final String METRICS_QUEUE = "metrics.queue";
    public static final String STATUS_QUEUE = "status.queue";

    @Bean
    public Queue metricsQueue() {
        return new Queue(METRICS_QUEUE, false);
    }

    @Bean
    public Queue statusQueue() {
        return new Queue(STATUS_QUEUE, false);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleMessageListenerContainer container(
            ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter
    ) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(STATUS_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(StatusListener receiver) {
        var adapter = new MessageListenerAdapter();

        adapter.setMessageConverter(jsonMessageConverter());
        adapter.setDefaultListenerMethod("processMessage");
        adapter.setDelegate(receiver);

        return adapter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}