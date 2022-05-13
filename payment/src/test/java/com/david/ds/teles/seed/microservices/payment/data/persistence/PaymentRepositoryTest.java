package com.david.ds.teles.seed.microservices.payment.data.persistence;

import com.david.ds.teles.seed.microservices.payment.config.TestContainersConfig;
import com.david.ds.teles.seed.microservices.payment.data.entities.PaymentEntity;
import com.david.ds.teles.seed.microservices.payment.enums.PaymentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentRepositoryTest extends TestContainersConfig {

    @MockBean
    private WebClient.Builder webClient;

    @Autowired
    private PaymentRepository repository;

    private PaymentEntity validPayment;

    @BeforeEach
    void setupDb() {
        repository.deleteAll();
        BigDecimal value = new BigDecimal("10.00");
        validPayment = new PaymentEntity(3333, value, PaymentStatus.PAID);

        validPayment = repository.save(validPayment);

        assertNotNull(validPayment);
        assertEquals(validPayment.getStatus(), PaymentStatus.PAID);
    }

    @Test
    void should_save_payment() {

        PaymentEntity payment = new PaymentEntity(null, 0, 3333, new BigDecimal("10.00"), null, PaymentStatus.PAID);
        PaymentEntity savedPayment = repository.save(payment);

        Optional<PaymentEntity> paymentOpt = repository.findById(savedPayment.getId());

        assertTrue(paymentOpt.isPresent());
        assertEquals(savedPayment, paymentOpt.get());

    }

    @Test
    void should_fetch_payment() {

        Optional<PaymentEntity> paymentOpt = repository.findById(validPayment.getId());

        assertTrue(paymentOpt.isPresent());
        assertEquals(validPayment, paymentOpt.get());

    }
}
