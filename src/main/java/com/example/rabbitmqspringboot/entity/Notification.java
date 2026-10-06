package com.example.rabbitmqspringboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table (name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String messageId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    private String status; //SENT, PENDING, FAILED
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime processedAt;

    @PrePersist
    public void prePersist(){
        if(createdAt == null){
            createdAt = LocalDateTime.now();
        }
        if(status == null){
            status = "PENDING";
        }
    }
}

