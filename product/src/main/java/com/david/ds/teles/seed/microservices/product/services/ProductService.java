package com.david.ds.teles.seed.microservices.product.services;

import com.david.ds.teles.seed.microservices.product.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO save(ProductDTO product);

    ProductDTO findById(String id);

    void delete(String id);

    void update(String id, ProductDTO product);

    List<ProductDTO> findAllById(List<String> products);
}
