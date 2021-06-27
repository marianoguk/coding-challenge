package com.n26.infrastructure.resource.documentation;

import com.n26.domain.dto.StatisticDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Transactions Real Time Statistics")
public interface StatisticsResource {
    @Operation(
            summary = "Get transactions real time statistics",
            tags = {"Transactions Real Time Statistics"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction has been created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StatisticDto.class))
                    })
    })
    ResponseEntity<StatisticDto> getStatistics();
}
