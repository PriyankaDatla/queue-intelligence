package com.queueintelligence.repository;

import com.queueintelligence.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository
        extends JpaRepository<Token, Long>{

    Long countByQueueQueueId(
            Long queueId);

    Long countByQueueQueueIdAndTokenNumberLessThanAndStatus(
            Long queueId,
            Integer tokenNumber,
            String status
    );

    Optional<Token>
    findFirstByQueueQueueIdAndStatusOrderByTokenNumberAsc(

            Long queueId,
            String status
    );

}