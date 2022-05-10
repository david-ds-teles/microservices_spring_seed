package com.david.ds.teles.seed.microservices.payment.dto;

import java.math.BigDecimal;

public record ProductDTO(String id, BigDecimal value) {

    public ProductDTO(String id) {
        this(id, null);
    }
}
