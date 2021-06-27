package com.n26.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TransactionCreator {

    public static TransactionDto createTx(long seconds) {
        TransactionDto dto = new TransactionDto();
        dto.setAmount(BigDecimal.TEN);
        dto.setTimestamp(date(seconds));
        return dto;
    }

    public static TransactionDto createTx(long seconds, String amount) {
        TransactionDto dto = createTx(seconds);
        dto.setAmount(new BigDecimal(amount));
        dto.setTimestamp(date(seconds));
        return dto;
    }

    private static LocalDateTime date(long seconds){
        return LocalDateTime.now(ZoneId.of("UTC")).plusSeconds(seconds);
    }
}
