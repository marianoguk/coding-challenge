package com.n26.infrastructure.config;

import com.n26.domain.service.StatisticsService;
import com.n26.domain.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TransactionService transactionService(@Value("${statistics.ttlInSeconds:60}") int ttlInSecs) {
        return new TransactionService(ttlInSecs);
    }

    @Bean
    public StatisticsService statisticsService(
            @Value("${statistics.ttlInSeconds:60}") int sizeInSecs,
            @Value("${statistics.slotSizeInMilliSeconds:1000}") long slotSizeInMilliSecs
    ) {
        return new StatisticsService(sizeInSecs, slotSizeInMilliSecs);
    }
}
