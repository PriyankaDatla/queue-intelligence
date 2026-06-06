package com.queueintelligence.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CounterRequest {

    private Long queueId;

    private String counterName;

    private Integer avgServiceTime;
}