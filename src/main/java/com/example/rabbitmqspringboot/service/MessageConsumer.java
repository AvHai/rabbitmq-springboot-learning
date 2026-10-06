package com.example.rabbitmqspringboot.service;

import com.example.rabbitmqspringboot.config.RabbitMQConfig;
import com.example.rabbitmqspringboot.entity.Message;
import com.example.rabbitmqspringboot.entity.Notification;
import com.example.rabbitmqspringboot.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleEmail(Message message) {
        log.info("Email notification received : ID = {} , Recipient = {} , Subject = {}", message.getId(), message.getRecipient(), message.getSubject());
        try {
            Thread.sleep(20000);

            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("SENT")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);
            log.info("Email notification processed and saved with ID : {}",message.getId());
        } catch (Exception e) {
            log.error("Email notification processing failed with message : {}", e.getMessage(),e);
            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("Failed")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.SMS_QUEUE)
    public void handleSms(Message message) {
        log.info("SMS notification received : ID = {} , Recipient = {} , Subject = {}", message.getId(), message.getRecipient(), message.getContent());
        try {
            Thread.sleep(5000);

            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("SENT")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);
            log.info("SMS notification processed and saved with ID : {}",message.getId());
        } catch (Exception e) {
            log.error("SMS notification processing failed with message : {}", e.getMessage(),e);
            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("Failed")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);
        }
    }
}
