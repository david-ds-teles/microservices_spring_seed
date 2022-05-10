package com.david.ds.teles.seed.microservices.payment.service;

import com.david.ds.teles.seed.microservices.payment.dto.PaymentDTO;
import reactor.core.publisher.Mono;

public interface PaymentService {

    Mono<PaymentDTO> pay(PaymentDTO payment);
}
