package com.example.rabbitmqspringboot.service;

import com.example.rabbitmqspringboot.config.RabbitMQConfig;
import com.example.rabbitmqspringboot.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public String sendEmailNotification(String recipient, String subject, String content, String priority) {
        String messageId = UUID.randomUUID().toString();

        Message message = Message.builder()
                .id(messageId)
                .type("EMAIL")
                .recipient(recipient)
                .subject(subject)
                .content(content)
                .priority("HIGH")
                .timeStamp(LocalDateTime.now())
                .build();

        try {
            log.info("Sending email notification with ID {} to RabbitMQ", messageId);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.APP_EXCHANGE,
                    RabbitMQConfig.EMAIL_NOTIFICATION_ROUTING_KEY,
                    message);
            log.info("Sent email notification with ID {} to RabbitMQ", messageId);
            return messageId;
        } catch (Exception e) {
            log.error("Error while sending email notification :{}", e.getMessage(),e);
            throw  new RuntimeException("Error while sending email notification");
        }
    }

    public String sendSmsNotification(String recipient, String content) {
        String messageId = UUID.randomUUID().toString();

        Message message = Message.builder()
                .id(messageId)
                .type("SMS")
                .recipient(recipient)
                .subject("SMS NOTIFICATION")
                .content(content)
                .priority("HIGH")
                .timeStamp(LocalDateTime.now())
                .build();

        try {
            log.info("Sending sms notification with ID {} to RabbitMQ", messageId);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.APP_EXCHANGE,
                    RabbitMQConfig.SMS_NOTIFICATION_ROUTING_KEY,
                    message);
            log.info("Sent sms notification with ID {} to RabbitMQ", messageId);
            return messageId;
        } catch (Exception e) {
            log.error("Error while sending sms notification :{}", e.getMessage(),e);
            throw  new RuntimeException("Error while sending sms notification");
        }
    }
}
