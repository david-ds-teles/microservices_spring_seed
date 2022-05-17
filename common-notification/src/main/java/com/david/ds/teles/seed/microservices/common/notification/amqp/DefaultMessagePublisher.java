package com.david.ds.teles.seed.microservices.common.notification.amqp;

import com.david.ds.teles.seed.microservices.common.notification.amqp.config.QueueConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
class DefaultMessagePublisher<T> implements MessagePublisher<T>{

    private final AmqpTemplate amqpTemplate;

    private QueueConfig config;

    public void publish(T payload) {
        log.info("sending message to exchange {} sugin routing-key: {} ", config.getExchange(), config.getRoutingKey());
        log.info("message payload: {}", payload);

        this.amqpTemplate.convertAndSend(config.getExchange(), config.getRoutingKey(), payload);

        log.info("message sent");
    }
}
