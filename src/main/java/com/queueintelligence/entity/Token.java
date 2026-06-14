package com.queueintelligence.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import com.queueintelligence.entity.enums.TokenStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queue queue;

    private Integer tokenNumber;

    private LocalDateTime joinTime;

    private LocalDateTime servedTime;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    private Integer estimatedWait;
}