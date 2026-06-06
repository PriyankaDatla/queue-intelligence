package com.queueintelligence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "counters")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long counterId;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queue queue;

    private String counterName;

    private Boolean isActive;

    private Integer avgServiceTime;
}