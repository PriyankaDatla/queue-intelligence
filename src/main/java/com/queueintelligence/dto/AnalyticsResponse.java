package com.queueintelligence.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsResponse {

    private long totalQueues;
    private long totalCounters;
    private long waitingTokens;
    private long servingTokens;
    private long completedTokens;
}