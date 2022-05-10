package com.david.ds.teles.seed.microservices.payment.data.persistence;

import com.david.ds.teles.seed.microservices.payment.config.TestContainersConfig;
import com.david.ds.teles.seed.microservices.payment.data.entities.PaymentEntity;
import com.david.ds.teles.seed.microservices.payment.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentRepositoryTest extends TestContainersConfig {

    private final Long validPaymentId = 1l;
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
        assertEquals(validPaymentId, validPayment.getId());
        assertEquals(validPayment.getStatus(), PaymentStatus.PAID);
    }

    @Test
    void should_fetch_payment() {

        Optional<PaymentEntity> paymentOpt = repository.findById(validPaymentId);

        assertTrue(paymentOpt.isPresent());
        assertEquals(validPayment, paymentOpt.get());

    }
}
