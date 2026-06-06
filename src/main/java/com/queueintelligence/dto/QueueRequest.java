package com.queueintelligence.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class QueueRequest {

    private String queueName;

    private String location;

    private String serviceType;
}