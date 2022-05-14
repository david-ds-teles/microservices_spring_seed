package com.david.ds.teles.seed.microservices.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.david.ds.teles.seed.microservices")
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.david.ds.teles.seed.microservices.clients"
)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
