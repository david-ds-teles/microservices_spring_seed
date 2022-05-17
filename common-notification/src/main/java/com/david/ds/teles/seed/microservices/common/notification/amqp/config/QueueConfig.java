package com.david.ds.teles.seed.microservices.common.notification.amqp.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class QueueConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String exchange;

    @Value("${rabbitmq.queue.notification}")
    private String queue;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String routingKey;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(this.exchange);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(this.queue);
    }

    @Bean
    public Binding bindingConfig() {
        return BindingBuilder
                .bind(this.notificationQueue())
                .to(this.topicExchange())
                .with(this.routingKey);
    }
}
