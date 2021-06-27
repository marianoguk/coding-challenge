package com.n26.domain.service;

import com.n26.domain.dto.TransactionDto;
import com.n26.domain.exception.ApiException;
import com.n26.domain.exception.ErrorType;
import com.n26.infrastructure.aop.TrackedEvent;
import com.n26.infrastructure.aop.TrackedEventType;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Slf4j
public class TransactionService {

    private int ttlInSecs;

    public TransactionService(int ttlInSecs){
        this.ttlInSecs = ttlInSecs;
    }


    @TrackedEvent(operation = TrackedEventType.NEW)
    public TransactionDto create(TransactionDto toCreate) {
        log.info("Adding new transaction: {}", toCreate);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        if(now.isBefore(toCreate.getTimestamp())) {
            throw new ApiException(ErrorType.TX_IS_IN_FUTURE);
        }
        LocalDateTime timeWindow = now.minus(ttlInSecs, ChronoUnit.SECONDS);

        if(timeWindow.isAfter(toCreate.getTimestamp())) {
            throw new ApiException(ErrorType.TX_OLDER_THAN_EXPECTED);
        }

        // Persistence logic should be here
        return new TransactionDto();
    }

    @TrackedEvent(operation = TrackedEventType.DELETE_ALL)
    public void deleteAll() {
        log.info("All transactions have been deleted");
    }
}
