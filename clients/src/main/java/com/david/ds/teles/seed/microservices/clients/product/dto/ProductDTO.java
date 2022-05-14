package com.david.ds.teles.seed.microservices.clients.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ProductDTO(String id, Integer version,
                         String name, BigDecimal value) {

    public ProductDTO(String id, String name, BigDecimal value) {
        this(id, null, name, value);
    }
}
