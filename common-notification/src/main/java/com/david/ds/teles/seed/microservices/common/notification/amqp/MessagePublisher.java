package com.david.ds.teles.seed.microservices.common.notification.amqp;

import org.springframework.stereotype.Component;

@Component
public interface MessagePublisher<T> {

    void publish(T payload);
}
