package com.david.ds.teles.seed.microservices.payment.dto;

import com.david.ds.teles.seed.microservices.payment.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record PaymentDTO(Long id, Integer cardNumber, BigDecimal value, PaymentStatus status, List<String> products) {

    public PaymentDTO(PaymentStatus status, BigDecimal value) {
        this(null, null, value, status, null);
    }

    public PaymentDTO(Integer cardNumber, List<String> products) {
        this(null, cardNumber, null, null, products);
    }

    public PaymentDTO(Long id, Integer cardNumber, List<String> products) {
        this(id, cardNumber, null, null, products);
    }

    public PaymentDTO(Integer cardNumber, BigDecimal value, PaymentStatus status, List<String> products) {
        this(null, cardNumber, value, null, products);
    }
}
