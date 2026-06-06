package com.queueintelligence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "queue_analytics")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class QueueAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analyticsId;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queue queue;

    private Double avgWaitTime;

    private Integer customersServed;

    private String peakHour;

    private LocalDate date;
}