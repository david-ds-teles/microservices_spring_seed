package com.david.ds.teles.seed.microservices.common.notification.amqp;

import java.util.List;

public record NotificationData(Type type, List<Channel> channels, String message) {

    public enum Type {
        PAYMENT
    }

    public enum Channel {
        EMAIL,
        PUSH,
        SMS
    }
}
