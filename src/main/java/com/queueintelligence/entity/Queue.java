package com.queueintelligence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "queues")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queueId;

    private String queueName;

    private String location;

    private String serviceType;

    private String status;

    private LocalDateTime createdAt;
}