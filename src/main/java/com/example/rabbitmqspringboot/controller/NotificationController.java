package com.example.rabbitmqspringboot.controller;

import com.example.rabbitmqspringboot.entity.Notification;
import com.example.rabbitmqspringboot.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @GetMapping("/getallnotification")
    public ResponseEntity<List<Notification>> getAllNotification() {
        List<Notification> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/getnotificationsbyrecipient")
    public ResponseEntity<List<Notification>> getNotificationsByRecipient(String recipient) {
        List<Notification> notificationsbyrecipient = notificationRepository.findByRecipientOrderByCreatedAtDesc(recipient);
        return ResponseEntity.ok(notificationsbyrecipient);
    }

    @GetMapping("/getnotificationsbystatus")
    public ResponseEntity<List<Notification>> getNotificationsByStatus(String status) {
        List<Notification> notificationsbystatus = notificationRepository.findByStatusOrderByCreatedAtDesc(status);
        return ResponseEntity.ok(notificationsbystatus);
    }
}

