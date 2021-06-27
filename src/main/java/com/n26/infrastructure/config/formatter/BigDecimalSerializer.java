package com.n26.infrastructure.config.formatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    private static final DecimalFormat FORMAT = new DecimalFormat("0.00");

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(FORMAT.format(value.setScale(2, RoundingMode.HALF_UP)));
    }
}
