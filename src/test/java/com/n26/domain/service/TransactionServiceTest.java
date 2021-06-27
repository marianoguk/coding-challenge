package com.n26.domain.service;

import com.n26.domain.dto.TransactionDto;
import com.n26.domain.exception.ApiException;
import com.n26.domain.exception.ErrorType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.n26.domain.service.TransactionCreator.createTx;

public class TransactionServiceTest {

    private static final int TTL = 60;

    private final TransactionService service = new TransactionService(TTL);

    @Test
    public void testOk(){
        TransactionDto dto = createTx(0);
        service.create(dto);
    }

    @Test
    public void testFailBecauseItIsInTheFuture(){
       validateErrorType(createTx(TTL*2), ErrorType.TX_IS_IN_FUTURE);
    }

    private void validateErrorType(TransactionDto dto, ErrorType type){
        try {
            service.create(dto);
            Assertions.fail();
        } catch (ApiException e) {
            Assertions.assertEquals(e.getType(), type);
        }
    }

    @Test
    public void testFailBecauseItIsOlderThanExpected(){
        validateErrorType(createTx(-TTL*2), ErrorType.TX_OLDER_THAN_EXPECTED);
    }
}
