package com.david.ds.teles.seed.microservices.payment.data.entities;

import com.david.ds.teles.seed.microservices.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    private int cardNumber;

    private BigDecimal value;

    @CreationTimestamp
    @PastOrPresent
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public PaymentEntity(int cardNumber, BigDecimal value, PaymentStatus status) {
        this.cardNumber = cardNumber;
        this.value = value;
        this.status = status;
    }
}
