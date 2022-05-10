package com.david.ds.teles.seed.microservices.product.mappers;

import com.david.ds.teles.seed.microservices.product.data.entities.ProductEntity;
import com.david.ds.teles.seed.microservices.product.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(ProductEntity entity);

    ProductEntity toEntity(ProductDTO dto);
}
