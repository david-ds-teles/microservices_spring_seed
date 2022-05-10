package com.david.ds.teles.seed.microservices.payment.api;

import com.david.ds.teles.seed.microservices.payment.api.specs.PaymentSpecsApi;
import com.david.ds.teles.seed.microservices.payment.dto.PaymentDTO;
import com.david.ds.teles.seed.microservices.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentApi implements PaymentSpecsApi {

    private PaymentService service;

    @Override
    public Mono<PaymentDTO> pay(PaymentDTO payment) {
        return service.pay(payment);
    }
}
