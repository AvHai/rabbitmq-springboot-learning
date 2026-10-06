package com.example.rabbitmqspringboot.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    // EXCHANGE
    public static final String APP_EXCHANGE = "app.exchange";

    // ROUTING KEY
    public static final String EMAIL_NOTIFICATION_ROUTING_KEY = "notification.email";
    public static final String SMS_NOTIFICATION_ROUTING_KEY = "notification.sms";

    // QUEUES
    public static final String EMAIL_QUEUE = "email.queue";
    public static final String SMS_QUEUE = "sms.queue";
    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(APP_EXCHANGE,true,false);
    }
    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE).build();
    }
    @Bean
    public Queue smsQueue() {
        return QueueBuilder.durable(SMS_QUEUE).build();
    }
    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(appExchange())
                .with(EMAIL_NOTIFICATION_ROUTING_KEY);
    }
    @Bean
    public Binding smsBinding() {
        return BindingBuilder.bind(smsQueue())
                .to(appExchange())
                .with(SMS_NOTIFICATION_ROUTING_KEY);
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConvertor());
        return rabbitTemplate;
    }
    @Bean
    public Jackson2JsonMessageConverter messageConvertor() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(mapper);
    }

}
