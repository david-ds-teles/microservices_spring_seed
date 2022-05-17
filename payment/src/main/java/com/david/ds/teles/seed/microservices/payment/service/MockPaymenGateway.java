package com.david.ds.teles.seed.microservices.payment.service;

import com.david.ds.teles.seed.microservices.clients.product.ProductClient;
import com.david.ds.teles.seed.microservices.common.notification.amqp.MessagePublisher;
import com.david.ds.teles.seed.microservices.common.notification.amqp.NotificationData;
import com.david.ds.teles.seed.microservices.commons.exceptions.MyExceptionError;
import com.david.ds.teles.seed.microservices.payment.data.entities.PaymentEntity;
import com.david.ds.teles.seed.microservices.payment.data.persistence.PaymentRepository;
import com.david.ds.teles.seed.microservices.payment.dto.PaymentDTO;
import com.david.ds.teles.seed.microservices.payment.enums.PaymentStatus;
import com.david.ds.teles.seed.microservices.payment.mappers.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.math.BigDecimal;
import java.util.List;

import static java.util.logging.Level.FINE;

@Service
@Slf4j
@AllArgsConstructor
public class MockPaymenGateway implements PaymentService {

    private final Scheduler threadScheduler;

    private final ProductClient productClient;

    private MessagePublisher<NotificationData> publisher;

    private PaymentRepository repository;

    private PaymentMapper mapper = Mappers.getMapper(PaymentMapper.class);

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

        return Flux.fromIterable(this.productClient.fetchAllById(payment.products()))
                .map(p -> p.value())
                .reduce((p1, p2) -> p1.add(p2))
                .log(log.getName(), FINE)
                .flatMap(total -> savePayment(payment, total))
                .subscribeOn(this.threadScheduler);
    }

    Mono<PaymentDTO> savePayment(PaymentDTO payment, BigDecimal total) {

        return Mono.fromCallable(() -> {
                    log.info("total sum of products are: {}", total);
                    PaymentEntity paid = repository.save(new PaymentEntity(payment.cardNumber(), total, PaymentStatus.PAID));
                    publisher.publish(
                            new NotificationData(
                                    NotificationData.Type.PAYMENT,
                                    List.of(NotificationData.Channel.EMAIL, NotificationData.Channel.PUSH),
                                    "payment received in the amount of  " + total + ". Paid with card: " + payment.cardNumber()
                            )
                    );
                    return paid;
                })
                .map(paid -> mapper.toDTO(paid))
                .subscribeOn(threadScheduler);
    }
}
