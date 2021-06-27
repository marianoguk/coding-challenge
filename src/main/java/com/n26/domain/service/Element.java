package com.n26.domain.service;

import com.n26.domain.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Element {
    public static final Element DEFAULT = new Element();

    private BigDecimal sum = BigDecimal.ZERO;
    private long count = 0L;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.ZERO;
    private long timestamp = 0L;

    public static Element update(Element current, TransactionDto dto, long slotSize) {
        Element newValue = new Element();
        long timestamp = toMilliSeconds(dto.getTimestamp());

        if (isFirst(current , timestamp, slotSize)) {
            newValue.timestamp = start(timestamp, slotSize);
            newValue.sum = dto.getAmount();
            newValue.count = 1;
            newValue.max = dto.getAmount();
            newValue.min = dto.getAmount();
        } else {
            newValue.timestamp = current.timestamp;
            newValue.sum = current.sum.add(dto.getAmount());
            newValue.count = current.count + 1;
            newValue.max = dto.getAmount().max(current.max);
            newValue.min = current.getCount() == 0 ? dto.getAmount() : dto.getAmount().min(current.getMin());
        }

        return newValue;
    }

    private static long toMilliSeconds(LocalDateTime date) {
        return date.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    private static boolean isFirst(Element current, long timestamp, long slotSize) {
        return current == null || current.isEmpty() || (timestamp - current.timestamp >= slotSize);
    }

    private boolean isEmpty() {
        return this == DEFAULT;
    }

    private static long start(long millis, long slotSize) {
        long offset = millis % slotSize;
        return millis - offset;
    }

    public static int slotNumber(LocalDateTime date, int size, long slotSize) {
        return Math.toIntExact((toMilliSeconds(date)/ slotSize) % size);
    }

    public static Element merge(Element e1, Element e2) {

        if (e1 == null) e1 = Element.DEFAULT;
        if (e2 == null) return e1;

        return new Element(
                e1.getSum().add(e2.getSum()),
                e1.getCount() + e2.getCount(),
                e1.getMax().compareTo(e2.getMax()) == 1 ? e1.getMax() : e2.getMax(),
                (e1.getCount() == 0 || e1.getMin().compareTo(e2.getMin()) ==1) ? e2.getMin() : e1.getMin(),
                e1.getTimestamp() < e2.getTimestamp() ? e1.getTimestamp() : e2.getTimestamp());
    };

    public BigDecimal getAvg() {
        return count == 0 ? BigDecimal.ZERO : sum.divide(new BigDecimal(count), 2, RoundingMode.HALF_UP);
    }
}
