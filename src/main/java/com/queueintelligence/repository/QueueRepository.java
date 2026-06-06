package com.queueintelligence.repository;

import com.queueintelligence.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository
        extends JpaRepository<Queue, Long> {
}