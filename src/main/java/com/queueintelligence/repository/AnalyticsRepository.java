package com.queueintelligence.repository;

import com.queueintelligence.entity.QueueAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository
        extends JpaRepository<QueueAnalytics, Long> {
}