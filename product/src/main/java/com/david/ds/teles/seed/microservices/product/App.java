package com.david.ds.teles.seed.microservices.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.david.ds.teles.seed.microservices")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
