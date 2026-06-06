package com.queueintelligence.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class JoinQueueRequest {

    private Long userId;

    private Long queueId;
}