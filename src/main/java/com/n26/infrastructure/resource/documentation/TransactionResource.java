package com.n26.infrastructure.resource.documentation;

import com.n26.domain.dto.TransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transactions")
public interface TransactionResource {
    @Operation(summary = "Create a transaction", tags = {"Transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction has been created"),
            @ApiResponse(responseCode = "204", description = "The transaction is older than the expected time"),
            @ApiResponse(responseCode = "400", description = "The JSON is invalid"),
            @ApiResponse(responseCode = "422", description = "Any of the fields are not parsable or the transaction date is in the future")
    })
    void create(TransactionDto toCreate);

    @Operation(summary = "Delete all transactions", tags = {"Transactions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transactions has been deleted successfully")
    })
    void delete();
}
