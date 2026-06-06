package com.queueintelligence.service;

import com.queueintelligence.dto.CounterRequest;
import com.queueintelligence.dto.QueueRequest;
import com.queueintelligence.dto.QueueStatusResponse;
import com.queueintelligence.entity.Counter;
import com.queueintelligence.entity.Queue;
import com.queueintelligence.entity.Token;
import com.queueintelligence.repository.CounterRepository;
import com.queueintelligence.repository.QueueRepository;
import com.queueintelligence.repository.UserRepository;
import com.queueintelligence.repository.TokenRepository;
import com.queueintelligence.dto.JoinQueueRequest;
import com.queueintelligence.entity.User;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {

    private final QueueRepository queueRepository;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final CounterRepository counterRepository;

    public QueueService(
            QueueRepository queueRepository,
            TokenRepository tokenRepository,
            UserRepository userRepository,
            CounterRepository counterRepository){

        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.counterRepository = counterRepository;
    }

    public String createQueue(
            QueueRequest request){

        Queue queue = Queue.builder()
                .queueName(request.getQueueName())
                .location(request.getLocation())
                .serviceType(request.getServiceType())
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .build();

        queueRepository.save(queue);

        return "Queue Created";
    }

    public List<Queue> getAllQueues(){

        return queueRepository.findAll();
    }

    public String joinQueue(
            JoinQueueRequest request){

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow();

        Queue queue = queueRepository
                .findById(request.getQueueId())
                .orElseThrow();

        Long count =
                tokenRepository.countByQueueQueueId(
                        queue.getQueueId());

        Token token = Token.builder()
                .user(user)
                .queue(queue)
                .tokenNumber(
                        count.intValue()+1)
                .joinTime(
                        LocalDateTime.now())
                .status("WAITING")
                .estimatedWait(
                        count.intValue()*5)
                .build();

        tokenRepository.save(token);

        return "Token Created : "
                + token.getTokenNumber();
    }

    public Token getTokenStatus(
            Long tokenId){

        return tokenRepository
                .findById(tokenId)
                .orElseThrow();
    }

    public QueueStatusResponse
    getQueueStatus(Long tokenId){

        Token token = tokenRepository
                .findById(tokenId)
                .orElseThrow();

        Long peopleAhead =
                tokenRepository
                        .countByQueueQueueIdAndTokenNumberLessThanAndStatus(

                                token.getQueue()
                                        .getQueueId(),

                                token.getTokenNumber(),

                                "WAITING"
                        );

        return QueueStatusResponse
                .builder()
                .tokenNumber(
                        token.getTokenNumber())
                .position(
                        peopleAhead.intValue())
                .estimatedWait(
                        peopleAhead.intValue()*5)
                .status(
                        token.getStatus())
                .build();
    }

    public String serveNextToken(
            Long queueId){

        Token token =
                tokenRepository

                        .findFirstByQueueQueueIdAndStatusOrderByTokenNumberAsc(

                                queueId,
                                "WAITING"
                        )

                        .orElse(null);

        if(token == null){

            return "No waiting tokens";
        }

        token.setStatus(
                "SERVING");

        token.setServedTime(
                LocalDateTime.now());

        tokenRepository.save(
                token);

        return "Serving Token : "
                + token.getTokenNumber();
    }

    public String openCounter(
            CounterRequest request){

        Queue queue =
                queueRepository
                        .findById(
                                request.getQueueId())
                        .orElseThrow();

        Counter counter =
                Counter.builder()

                        .queue(queue)

                        .counterName(
                                request.getCounterName())

                        .avgServiceTime(
                                request.getAvgServiceTime())

                        .isActive(true)

                        .build();

        counterRepository.save(counter);

        return "Counter Opened";
    }

    public String closeCounter(
            Long counterId){

        Counter counter =
                counterRepository
                        .findById(counterId)
                        .orElseThrow();

        counter.setIsActive(false);

        counterRepository.save(counter);

        return "Counter Closed";
    }
}