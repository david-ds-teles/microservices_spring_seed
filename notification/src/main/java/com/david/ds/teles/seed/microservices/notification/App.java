package com.david.ds.teles.seed.microservices.notification;

import com.david.ds.teles.seed.microservices.common.notification.amqp.MessagePublisher;
import com.david.ds.teles.seed.microservices.common.notification.amqp.NotificationData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan("com.david.ds.teles.seed.microservices")
@EnableEurekaClient
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner messageTest(MessagePublisher<NotificationData> pub) {
        return args -> {
            pub.publish(new NotificationData(
                    NotificationData.Type.PAYMENT,
                    List.of(NotificationData.Channel.EMAIL, NotificationData.Channel.PUSH),
                    "simple notification test")
            );
        };
    }
}
