package com.n26.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.infrastructure.config.formatter.BigDecimalSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TransactionDto {
    @JsonSerialize(using = BigDecimalSerializer.class)
    @NotNull
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @NotNull
    @PastOrPresent(message = "The date cannot be in the future")
    private LocalDateTime timestamp;
}
