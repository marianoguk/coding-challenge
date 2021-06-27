package com.n26.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.infrastructure.config.formatter.BigDecimalSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class StatisticDto {
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal sum;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal avg;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal max;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal min;
    private Long count;
}
