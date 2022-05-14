package com.david.ds.teles.seed.microservices.payment.service;

import com.david.ds.teles.seed.microservices.clients.product.dto.ProductDTO;
import reactor.core.publisher.Flux;

import java.util.List;

@Deprecated
public interface ProductIntegrationService {
    Flux<ProductDTO> findAllById(List<String> productsId);
}
