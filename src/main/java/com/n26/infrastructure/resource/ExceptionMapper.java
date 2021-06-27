package com.n26.infrastructure.resource;

import com.n26.domain.exception.ApiException;
import com.n26.domain.exception.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionMapper {
    private static final Map<ErrorType, HttpStatus> errorMapping = errorMapping();
    /**
     * 201 – in case of success
     * 204 – if the transaction is older than 60 seconds
     * 400 – if the JSON is invalid
     * 422 – if any of the fields are not parsable or the transaction date is in the future
     */
    private static Map<ErrorType, HttpStatus> errorMapping() {
        Map<ErrorType, HttpStatus> result = new HashMap();
        result.put(ErrorType.TX_OLDER_THAN_EXPECTED, HttpStatus.valueOf(204));
        result.put(ErrorType.TX_INVALID_FORMAT, HttpStatus.valueOf(400));
        result.put(ErrorType.TX_IS_IN_FUTURE, HttpStatus.valueOf(422));
        return result;
    }

    public HttpStatus map(ApiException e) {
        return errorMapping().getOrDefault(e.getType(), HttpStatus.BAD_REQUEST);
    }
}
