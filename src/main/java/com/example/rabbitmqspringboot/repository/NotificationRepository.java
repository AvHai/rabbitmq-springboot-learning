package com.example.rabbitmqspringboot.repository;

import com.example.rabbitmqspringboot.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByRecipientOrderByCreatedAtDesc(String recipient);

    List<Notification> findByStatusOrderByCreatedAtDesc(String status);
}
