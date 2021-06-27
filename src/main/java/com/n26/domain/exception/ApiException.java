package com.n26.domain.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "Error type = '%s'";
    private final ErrorType type;

    public ApiException(ErrorType type) {
        super(String.format(MESSAGE_FORMAT, type));
        this.type = type;
    }

}
