package com.queueintelligence.repository;

import com.queueintelligence.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository
        extends JpaRepository<Counter, Long> {
}