package com.david.ds.teles.seed.microservices.product.mappers;

import com.david.ds.teles.seed.microservices.product.data.entities.ProductEntity;
import com.david.ds.teles.seed.microservices.product.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductMapperTest {
    private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void mapperTests() {

        assertNotNull(mapper);

        ProductDTO dto = new ProductDTO("1", 1, "test", BigDecimal.TEN);

        ProductEntity entity = mapper.toEntity(dto);

        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.version(), entity.getVersion());

        ProductDTO dtoCheck = mapper.toDTO(entity);

        assertEquals(dtoCheck.id(), entity.getId());
        assertEquals(dtoCheck.name(),      entity.getName());
        assertEquals(dtoCheck.version(),    entity.getVersion());
    }
}
