package com.queueintelligence.controller;

import com.queueintelligence.dto.CounterRequest;
import com.queueintelligence.dto.JoinQueueRequest;
import com.queueintelligence.dto.QueueRequest;
import com.queueintelligence.dto.QueueStatusResponse;
import com.queueintelligence.entity.Queue;
import com.queueintelligence.entity.Token;
import com.queueintelligence.service.QueueService;
import org.springframework.web.bind.annotation.*;
import com.queueintelligence.dto.CounterRequest;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/queues")

public class QueueController {

    private final QueueService queueService;

    public QueueController(
            QueueService queueService){

        this.queueService = queueService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createQueue(
            @RequestBody QueueRequest request) {
        return queueService.createQueue(request);
    }

    @GetMapping

    public List<Queue> getQueues(){

        return queueService
                .getAllQueues();
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/join")
    public String joinQueue(
            @RequestBody JoinQueueRequest request) {

        return queueService.joinQueue(request);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/status/{tokenId}")
    public QueueStatusResponse getQueueStatus(
            @PathVariable Long tokenId) {

        return queueService.getQueueStatus(tokenId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/serve-next/{queueId}")
    public String serveNext(
            @PathVariable Long queueId) {

        return queueService.serveNextToken(queueId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/counter/open")
    public String openCounter(
            @RequestBody CounterRequest request) {

        return queueService.openCounter(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/counter/close/{counterId}")
    public String closeCounter(
            @PathVariable Long counterId) {

        return queueService.closeCounter(counterId);
    }

    @GetMapping("/tokens/{userId}")
    public List<Token> getUserTokens(
            @PathVariable Long userId) {

        return queueService.getUserTokens(userId);
    }
}