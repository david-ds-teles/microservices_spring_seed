package com.david.ds.teles.seed.microservices.payment.service;

import com.david.ds.teles.seed.microservices.payment.dto.ProductDTO;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProductIntegrationService {
    Flux<ProductDTO> findAllById(List<String> productsId);
}
