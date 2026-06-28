package com.queueintelligence.repository;

import com.queueintelligence.entity.Token;
import com.queueintelligence.entity.enums.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository
        extends JpaRepository<Token, Long> {
    Long countByStatus(TokenStatus status);
    Long countByQueueQueueId(
            Long queueId);
    List<Token> findByUserUserId(Long userId);
    Long countByQueueQueueIdAndTokenNumberLessThanAndStatus(
            Long queueId,
            Integer tokenNumber,
            TokenStatus status
    );

    Optional<Token>
    findFirstByQueueQueueIdAndStatusOrderByTokenNumberAsc(

            Long queueId,
            TokenStatus status
    );
}