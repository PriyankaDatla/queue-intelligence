package com.queueintelligence.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class QueueStatusResponse {

    private Integer tokenNumber;

    private Integer position;

    private Integer estimatedWait;

    private String status;
}