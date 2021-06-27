package com.n26.infrastructure.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.n26.domain.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionMapper errorMapper;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleAllUncaughtException(Exception exception, WebRequest request) {
        log.error("Handling exception {}", exception.getMessage(), exception);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public void handleJsonInvalid(InvalidFormatException exception) {
        log.error("Handling Json Parsing error {}", exception.getMessage(), exception);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Void> handleDomainException(ApiException exception, WebRequest request) {
        log.error("Handling Domain exception {}", exception.getMessage(), exception);
        return ResponseEntity.status(errorMapper.map(exception)).build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Void> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("Http message not readable", ex);
        if (ex.getCause() instanceof InvalidFormatException) {
            return ResponseEntity.status(HttpStatus.valueOf(422)).build();
        }

        if( ex.getCause() instanceof MismatchedInputException) {
            return ResponseEntity.status(HttpStatus.valueOf(400)).build();
        }


        if (ex.getCause() instanceof DateTimeParseException) {
            return ResponseEntity.status(HttpStatus.valueOf(422)).build();
        }
        return ResponseEntity.status(HttpStatus.valueOf(422)).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public void handleGenericInputError(JsonProcessingException formatException){
        log.error("Json Parsing error", formatException);
    }

    @ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MismatchedInputException.class})
    public void handleMismatchedInputException(Exception formatException){
        log.error("Json Parsing error", formatException);
    }
}