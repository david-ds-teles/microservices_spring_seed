package com.david.ds.teles.seed.microservices.product.services;

import com.david.ds.teles.seed.microservices.commons.exceptions.MyExceptionError;
import com.david.ds.teles.seed.microservices.product.data.entities.ProductEntity;
import com.david.ds.teles.seed.microservices.product.data.persistence.ProductRepository;
import com.david.ds.teles.seed.microservices.product.dto.ProductDTO;
import com.david.ds.teles.seed.microservices.product.mappers.ProductMapper;
import com.david.ds.teles.seed.microservices.commons.i18n.AppMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository repository;

    @Spy
    ProductMapper mapper;

    @Spy
    AppMessage messages;

    @InjectMocks
    ProductServiceImp service;

    ProductDTO dto;

    ProductEntity entity;

    @BeforeEach
    void initial_setup() {
        dto = new ProductDTO("1", "test", BigDecimal.TEN);
        entity = new ProductEntity(dto.id(), 1, dto.name(), dto.value());
    }

    @Test
    void should_save_product() {
        // when
        ProductEntity entity = new ProductEntity(dto.id(), dto.name(), BigDecimal.TEN);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(new ProductDTO(dto.id(), 0, dto.name(), dto.value()));
        when(repository.save(any())).thenReturn(entity);

        // then
        ProductDTO result = service.save(dto);

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toDTO(any());
        verify(repository, times(1)).save(any());

        assertNotNull(result);
        assertEquals(dto.id(), result.id());
        assertEquals(dto.name(), result.name());
    }

    @Test
    void should_update_product() {
        // given
        String productId = "1";
        String nonExistentProductId = "13";

        // when
        when(repository.findById(productId)).thenReturn(Optional.of(entity));
        when(repository.findById(nonExistentProductId)).thenReturn(Optional.empty());

        // then
        service.update(productId, dto);

        verify(repository, times(1)).save(any());

        assertThrows(MyExceptionError.class, () -> {
            service.update(nonExistentProductId, dto);
        });
    }

    @Test
    void find_by_id() {

        // given
        String productId = "1";
        String nonExistentProductId = "13";

        // when
        when(repository.findById(productId)).thenReturn(Optional.of(entity));
        when(repository.findById(nonExistentProductId)).thenReturn(Optional.empty());
        when(mapper.toDTO(entity)).thenReturn(dto);

        // then
        ProductDTO result = service.findById(productId);

        verify(repository, times(1)).findById(productId);
        verify(mapper, times(1)).toDTO(entity);
        assertNotNull(result);

        assertThrows(MyExceptionError.class, () -> {
            service.findById(nonExistentProductId);
        });
    }

    @Test
    void find_all_by_id() {

        // given
        List<String> productsId = List.of("1", "2", "3");

        // when
        when(repository.findAllById(productsId))
                .thenReturn(List.of(new ProductEntity("1", 0, "test1", BigDecimal.TEN),
                        new ProductEntity("2", 0, "test2", BigDecimal.TEN),
                        new ProductEntity("3", 0, "test3", BigDecimal.TEN)));

        when(repository.findAll())
                .thenReturn(List.of(new ProductEntity("1", 0, "test1", BigDecimal.TEN),
                        new ProductEntity("2", 0, "test2", BigDecimal.TEN),
                        new ProductEntity("3", 0, "test3", BigDecimal.TEN)));

        // then
        List<ProductDTO> result = service.findAllById(productsId);

        verify(repository, times(1)).findAllById(productsId);
        verify(mapper, times(3)).toDTO(any(ProductEntity.class));
        assertNotNull(result);
        assertEquals(3, result.size());

        List<ProductDTO> result2 = service.findAllById(List.of());
        verify(repository, times(1)).findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void should_delete_a_product() {

        // given
        String productId = "1";
        String nonExistentProductId = "13";

        // when
        when(repository.findById(productId)).thenReturn(Optional.of(entity));
        when(repository.findById(nonExistentProductId)).thenReturn(Optional.empty());

        // then
        service.delete(productId);

        verify(repository, times(1)).findById(productId);
        verify(repository, times(1)).delete(entity);

        assertThrows(MyExceptionError.class, () -> {
            service.delete(nonExistentProductId);
        });
    }
}
