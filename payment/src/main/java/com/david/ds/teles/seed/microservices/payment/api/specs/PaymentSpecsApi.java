package com.david.ds.teles.seed.microservices.payment.api.specs;

import com.david.ds.teles.seed.microservices.payment.dto.PaymentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Payment", description = "Payment api")
public interface PaymentSpecsApi {

    @Operation(
            summary = "Starting Payment",
            description = "Create a payment request"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "202", description = "${api.rsp.ok}"),
                    @ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
            }
    )
    @PostMapping
    Mono<PaymentDTO> pay(@RequestBody PaymentDTO payment);

}
