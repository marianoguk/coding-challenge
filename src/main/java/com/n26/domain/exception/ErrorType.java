package com.n26.domain.exception;

public enum ErrorType {
    /**
     * 201 – in case of success
     * 204 – if the transaction is older than 60 seconds
     * 400 – if the JSON is invalid
     * 422 – if any of the fields are not parsable or the transaction date is in the future
     */
    TX_OLDER_THAN_EXPECTED,
    TX_INVALID_FORMAT,
    TX_IS_IN_FUTURE;

}
