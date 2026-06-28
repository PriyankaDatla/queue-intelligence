package com.queueintelligence.service;

import com.queueintelligence.dto.*;
import com.queueintelligence.entity.Counter;
import com.queueintelligence.entity.Queue;
import com.queueintelligence.entity.Token;
import com.queueintelligence.entity.enums.TokenStatus;
import com.queueintelligence.exception.ResourceNotFoundException;
import com.queueintelligence.repository.CounterRepository;
import com.queueintelligence.repository.QueueRepository;
import com.queueintelligence.repository.UserRepository;
import com.queueintelligence.repository.TokenRepository;
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
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Queue queue = queueRepository
                .findById(request.getQueueId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found"));

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
                .status(TokenStatus.WAITING)
                .estimatedWait(
                        count.intValue()*5)
                .build();

        tokenRepository.save(token);

        return "Token Created : "
                + token.getTokenNumber();
    }

    public List<Token> getUserTokens(Long userId) {

        return tokenRepository.findByUserUserId(userId);
    }

    public Token getTokenStatus(
            Long tokenId){

        return tokenRepository
                .findById(tokenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Token not found"));
    }

    public QueueStatusResponse
    getQueueStatus(Long tokenId){

        Token token = tokenRepository
                .findById(tokenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Token not found"));

        Long peopleAhead =
                tokenRepository
                        .countByQueueQueueIdAndTokenNumberLessThanAndStatus(

                                token.getQueue()
                                        .getQueueId(),

                                token.getTokenNumber(),
                                TokenStatus.WAITING
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
                        token.getStatus().name())
                .build();
    }

    public String serveNextToken(
            Long queueId){

        Token token =
                tokenRepository

                        .findFirstByQueueQueueIdAndStatusOrderByTokenNumberAsc(

                                queueId,
                                TokenStatus.WAITING
                        )

                        .orElse(null);

        if(token == null){

            return "No waiting tokens";
        }

        token.setStatus(
                TokenStatus.SERVING);

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
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Queue not found"));

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
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Counter not found"));

        counter.setIsActive(false);

        counterRepository.save(counter);

        return "Counter Closed";
    }

    public AnalyticsResponse getAnalytics() {

        return AnalyticsResponse.builder()
                .totalQueues(queueRepository.count())
                .totalCounters(counterRepository.count())
                .waitingTokens(tokenRepository.countByStatus(TokenStatus.WAITING))
                .servingTokens(tokenRepository.countByStatus(TokenStatus.SERVING))
                .completedTokens(tokenRepository.countByStatus(TokenStatus.COMPLETED))
                .build();
    }
}