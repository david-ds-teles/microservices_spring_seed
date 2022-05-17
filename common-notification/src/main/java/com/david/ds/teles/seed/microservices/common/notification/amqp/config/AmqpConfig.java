package com.david.ds.teles.seed.microservices.common.notification.amqp.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AmqpConfig {

    private final ConnectionFactory connFactory;

    @Bean
    public AmqpTemplate amqpTemplate() {
        RabbitTemplate template = new RabbitTemplate(this.connFactory);
        template.setMessageConverter(jacksonConverter());

        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory() {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(this.connFactory);
        containerFactory.setMessageConverter(this.jacksonConverter());

        return containerFactory;
    }

    @Bean
    public MessageConverter jacksonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        return converter;
    }
}
