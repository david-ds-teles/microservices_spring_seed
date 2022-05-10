package com.david.ds.teles.seed.microservices.product.data.persistence;

import com.david.ds.teles.seed.microservices.product.data.entities.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {

    @Override
    @Transactional(readOnly = true)
    Optional<ProductEntity> findById(String s);
}
