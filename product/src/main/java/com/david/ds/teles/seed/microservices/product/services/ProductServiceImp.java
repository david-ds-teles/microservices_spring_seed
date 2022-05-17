package com.david.ds.teles.seed.microservices.product.services;

import com.david.ds.teles.seed.microservices.commons.exceptions.MyExceptionError;
import com.david.ds.teles.seed.microservices.commons.i18n.AppMessage;
import com.david.ds.teles.seed.microservices.product.data.entities.ProductEntity;
import com.david.ds.teles.seed.microservices.product.data.persistence.ProductRepository;
import com.david.ds.teles.seed.microservices.product.dto.ProductDTO;
import com.david.ds.teles.seed.microservices.product.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductMapper mapper;
    private ProductRepository repository;

    private AppMessage messageBundle;

    @Override
    public ProductDTO save(ProductDTO product) {

        try {
            ProductEntity productEntity = mapper.toEntity(product);
            productEntity = repository.save(productEntity);
            log.info("product created {}", productEntity);
            ProductDTO result = mapper.toDTO(productEntity);
            return result;
        } catch (DuplicateKeyException dke) {
            throw new MyExceptionError(messageBundle.getMessage("duplicated_product_id", new String[]{product.id()}));
        }
    }

    @Override
    public void update(String id, @NotNull ProductDTO product) {
        ProductEntity entity = repository.findById(id).orElseThrow(() -> new MyExceptionError(messageBundle.getMessage("product_not_found", new String[]{id}), 404));
        entity.setName(product.name());
        entity.setValue(product.value());
        repository.save(entity);
        log.info("product updated {}", entity);
    }

    @Override
    public ProductDTO findById(String id) {
        ProductEntity entity = repository.findById(id).orElseThrow(() -> new MyExceptionError(messageBundle.getMessage("product_not_found", new String[]{id}), 404));
        log.info("product found {}", entity);
        ProductDTO dto = mapper.toDTO(entity);
        return dto;
    }

    @Override
    public List<ProductDTO> findAllById(List<String> products) {
        log.info("starting findAllById for ids {} ", products);

        Iterable<ProductEntity> iterator;

        if (products != null && !products.isEmpty())
            iterator = repository.findAllById(products);
        else
            iterator = repository.findAll();

        Stream<ProductEntity> resultStream = StreamSupport.stream(iterator.spliterator(), false);
        List<ProductDTO> result = resultStream.map(p -> mapper.toDTO(p)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void delete(String id) {
        ProductEntity entity = repository.findById(id).orElseThrow(() -> new MyExceptionError(messageBundle.getMessage("product_not_found", new String[]{id}), 404));
        repository.delete(entity);
        log.info("product deleted {}", entity);
    }
}
