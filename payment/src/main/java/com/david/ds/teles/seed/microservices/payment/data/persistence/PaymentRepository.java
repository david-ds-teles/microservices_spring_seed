package com.david.ds.teles.seed.microservices.payment.data.persistence;

import com.david.ds.teles.seed.microservices.payment.data.entities.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
}
