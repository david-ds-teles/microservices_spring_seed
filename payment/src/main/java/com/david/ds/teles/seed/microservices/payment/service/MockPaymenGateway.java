package com.david.ds.teles.seed.microservices.payment.service;

import com.david.ds.teles.seed.microservices.payment.data.entities.PaymentEntity;
import com.david.ds.teles.seed.microservices.payment.data.persistence.PaymentRepository;
import com.david.ds.teles.seed.microservices.payment.dto.PaymentDTO;
import com.david.ds.teles.seed.microservices.payment.enums.PaymentStatus;
import com.david.ds.teles.seed.microservices.payment.exceptions.MyExceptionError;
import com.david.ds.teles.seed.microservices.payment.mappers.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class MockPaymenGateway implements PaymentService {

    private ProductIntegrationService productIntegration;

    private PaymentRepository repository;

    private PaymentMapper mapper;

    @Override
    public Mono<PaymentDTO> pay(PaymentDTO payment) {
        if (payment.cardNumber() == null || payment.cardNumber() <= 0)
            throw new MyExceptionError("invalid credit card number: " + payment.cardNumber(), 400);

        final String cardNumber = String.valueOf(payment.cardNumber());

        if (cardNumber.startsWith("1111"))
            throw new MyExceptionError("credit card expired: " + payment.cardNumber(), 400);

        if (cardNumber.startsWith("2222"))
            throw new MyExceptionError("credit card rejected: " + payment.cardNumber(), 400);

        if (payment.products() == null || payment.products().isEmpty())
            throw new MyExceptionError("products invalid", 400);

        Mono<PaymentDTO> result = productIntegration.findAllById(payment.products())
                .map(p -> p.value())
                .reduce((p1, p2) -> p1.add(p2))
                .flatMap(total -> {
                    log.info("total sum of products are: {}", total);
                    PaymentEntity paid = repository.save(new PaymentEntity(payment.cardNumber(), total, PaymentStatus.PAID));
                    log.info("payment successfully: {}", paid);
                    return Mono.just(paid).map(mapper::toDTO);
                });

        return result;
    }

//    @Override
//    public Mono<PaymentDTO> pay(PaymentDTO payment) {
//        return Mono.create(sink -> {
//            if (payment.cardNumber() == null || payment.cardNumber() <= 0)
//                sink.error(new MyExceptionError("invalid credit card number: " + payment.cardNumber(), 400));
//
//            final String cardNumber = String.valueOf(payment.cardNumber());
//
//            if (cardNumber.startsWith("1111")){
//                sink.error(new MyExceptionError("credit card expired: " + payment.cardNumber(), 400));
//                return;
//            }
//
//
//            if (cardNumber.startsWith("2222")){
//                sink.error(new MyExceptionError("credit card reject: " + payment.cardNumber(), 400));
//                return;
//            }
//
//            if(payment.products() == null || payment.products().isEmpty()) {
//                sink.error(new MyExceptionError("products invalid", 400));
//                return;
//            }
//
//            Flux<ProductDTO> subscription = webClient.post()
//                    .uri("http://localhost:8080/product")
//                    .body(BodyInserters.fromValue(payment.products()))
//                    .retrieve()
//                    .bodyToFlux(ProductDTO.class)
//                    .log(log.getName(), FINE)
//                    .onErrorMap(WebClientResponseException.class, ex -> webClientRspErrorHandler(ex));
//
//            subscription.subscribe(product -> {
//                log.info("product found: {}", product);
//            });
//
//            log.info("payment successfully");
//
//            sink.success(new PaymentDTO(PaymentStatus.PAID));
//
//        });
//    }


}
