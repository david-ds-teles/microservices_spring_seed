package com.david.ds.teles.seed.microservices.notification;

import com.david.ds.teles.seed.microservices.common.notification.amqp.NotificationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class MessageConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumer(NotificationData data) {
        log.info("starting message consumer for {}", data);
        log.info("ending message consumer");
    }
}
