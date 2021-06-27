package com.n26.domain.service;

import com.n26.domain.dto.StatisticDto;
import com.n26.domain.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.IntStream;

@Slf4j
public class StatisticsService {
    private AtomicReferenceArray<Element> elements;
    private int size;
    private long slotSize;

    public StatisticsService(int sizeInSecs, long slotSizeInMilliSecs) {
        this.size = sizeInSecs;
        this.elements = new AtomicReferenceArray(sizeInSecs);
        this.slotSize = slotSizeInMilliSecs;
    }

    public void process(TransactionDto tx) {
       log.info("Processing new transaction: {}", tx);
        int index = Element.slotNumber(tx.getTimestamp(), size, slotSize);
        elements.getAndUpdate(index, prev -> Element.update(prev, tx, slotSize));
    }

    public StatisticDto getStatistic() {
        LocalDateTime nowLocalDateTime = LocalDateTime.now(ZoneId.of("UTC"));
        long now = nowLocalDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        Element merged = IntStream.range(0,size)
                .mapToObj(elements::get)
                .filter(Objects::nonNull)
                .filter(e -> isAlive(e, now))
                .reduce(Element.DEFAULT, Element::merge);
        return buildStatistics(merged);
    }

    private boolean isAlive(Element e, long now) {
        return now - e.getTimestamp() < size * slotSize;
    }

    private StatisticDto buildStatistics(Element merged) {
        return new StatisticDto(
            merged.getSum(), merged.getAvg(), merged.getMax(), merged.getMin(), merged.getCount()
        );
    }

    public void deleteAll() {
        log.info("All transactions have been deleted");
        this.elements = new AtomicReferenceArray(size);
    }
}
