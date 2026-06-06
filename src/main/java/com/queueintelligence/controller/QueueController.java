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

@RestController
@RequestMapping("/queues")

public class QueueController {

    private final QueueService queueService;

    public QueueController(
            QueueService queueService){

        this.queueService = queueService;
    }

    @PostMapping

    public String createQueue(
            @RequestBody QueueRequest request){

        return queueService
                .createQueue(request);
    }

    @GetMapping

    public List<Queue> getQueues(){

        return queueService
                .getAllQueues();
    }

    @PostMapping("/join")

    public String joinQueue(

            @RequestBody JoinQueueRequest request){

        return queueService
                .joinQueue(request);
    }

    @GetMapping("/status/{tokenId}")

    public QueueStatusResponse
    getQueueStatus(

            @PathVariable Long tokenId){

        return queueService
                .getQueueStatus(tokenId);
    }

    @PostMapping("/serve-next/{queueId}")

    public String serveNext(

            @PathVariable Long queueId){

        return queueService
                .serveNextToken(
                        queueId);
    }

    @PostMapping("/counter/open")

    public String openCounter(

            @RequestBody CounterRequest request){

        return queueService
                .openCounter(request);
    }

    @PostMapping("/counter/close/{counterId}")

    public String closeCounter(

            @PathVariable Long counterId){

        return queueService
                .closeCounter(counterId);
    }
}