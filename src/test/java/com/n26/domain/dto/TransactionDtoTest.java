package com.n26.domain.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class TransactionDtoTest {

    private static final String NULL_ERROR_MESSAGE = "must not be null";
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validateTimestampIsNotNull(){
        var dto = new TransactionDto();
        dto.setAmount(BigDecimal.ONE);
        var validations = validator.validate(dto);
        assertThat(validations.size(), comparesEqualTo(1));
        var nullityConstraint = validations.stream().findFirst().get();
        validateNullityConstraint(nullityConstraint, "timestamp");
    }

    @Test
    public void validateTimestampInTheFuture(){
        var dto = new TransactionDto();
        dto.setAmount(BigDecimal.ONE);
        dto.setTimestamp(LocalDateTime.now().plusSeconds(123));
        var validations = validator.validate(dto);
        assertThat(validations.size(), comparesEqualTo(1));
        var nullityConstraint = validations.stream().findFirst().get();
        validateConstraint(nullityConstraint, "timestamp", "The date cannot be in the future");
    }

    @Test
    public void validateAmountIsNotNull(){
        var dto = new TransactionDto();
        dto.setTimestamp(LocalDateTime.now());
        var validations = validator.validate(dto);
        assertThat(validations.size(), comparesEqualTo(1));
        var nullityConstraint = validations.stream().findFirst().get();
        validateNullityConstraint(nullityConstraint, "amount");
    }

    private void validateConstraint(ConstraintViolation<TransactionDto> constraint, String field, String message){
        assertThat(constraint.getMessage(), comparesEqualTo(message));
        assertThat(constraint.getPropertyPath().toString(), comparesEqualTo(field));
    }

    private void validateNullityConstraint(ConstraintViolation<TransactionDto> constraint, String field){
        validateConstraint(constraint, field, NULL_ERROR_MESSAGE);
    }
}
