package com.david.ds.teles.seed.microservices.product.data.persistence;

import com.david.ds.teles.seed.microservices.product.config.TestContainersConfig;
import com.david.ds.teles.seed.microservices.product.data.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class ProductRepositoryTest extends TestContainersConfig {

    @Autowired
    private ProductRepository repository;

    private ProductEntity initialEntitySaved;

    @BeforeEach
    void initial_setup() {
        repository.deleteAll();
        assertEquals(0, repository.count());

        ProductEntity entity = new ProductEntity("1", "test", BigDecimal.TEN);
        initialEntitySaved = repository.save(entity);

        assertEqualsProduct(entity, initialEntitySaved);
    }


    @Test
    void should_create_a_new_product() {

        ProductEntity entity = new ProductEntity("2", "test", BigDecimal.TEN);
        repository.save(entity);

        ProductEntity foundEntity = repository.findById(entity.getId()).get();
        assertEqualsProduct(entity, foundEntity);

        assertEquals(2, repository.count());
    }

    @Test
    void should_update_a_previous_saved_product() {
        initialEntitySaved.setName("test_updated");
        repository.save(initialEntitySaved);

        ProductEntity foundEntity = repository.findById(initialEntitySaved.getId()).get();
        assertEquals(1, (long) foundEntity.getVersion());
        assertEquals("test_updated", foundEntity.getName());
    }

    @Test
    void should_delete_a_product() {
        repository.delete(initialEntitySaved);
        assertFalse(repository.existsById(initialEntitySaved.getId()));
    }

    @Test
    void should_fetch_a_product_by_id() {
        Optional<ProductEntity> entity = repository.findById(initialEntitySaved.getId());

        assertTrue(entity.isPresent());
        assertEqualsProduct(initialEntitySaved, entity.get());
    }

    @Test
    void throw_error_when_id_is_already_in_use() {
        assertThrows(DuplicateKeyException.class, () -> {
            ProductEntity entity = new ProductEntity(initialEntitySaved.getId(), "test", BigDecimal.TEN);
            repository.save(entity);
        });
    }

    @Test
    void throw_error_when_version_is_less_than_current_saved() {

        ProductEntity entity1 = repository.findById(initialEntitySaved.getId()).get();
        ProductEntity entity2 = repository.findById(initialEntitySaved.getId()).get();

        entity1.setName("updated");
        repository.save(entity1);

        assertThrows(OptimisticLockingFailureException.class, () -> {
            entity2.setName("updated_2");
            repository.save(entity2);
        });

        ProductEntity updatedEntity = repository.findById(initialEntitySaved.getId()).get();
        assertEquals(1, (long) updatedEntity.getVersion());
        assertEquals("updated", updatedEntity.getName());
    }

    @Test
    void should_paging_results() {

        repository.deleteAll();

        List<ProductEntity> newProducts = rangeClosed(1001, 1010)
                .mapToObj(i -> new ProductEntity(i + "", "test " + i, BigDecimal.TEN))
                .collect(Collectors.toList());
        repository.saveAll(newProducts);

        Pageable nextPage = PageRequest.of(0, 4, ASC, "id");
        nextPage = browserPage(nextPage, "[1001, 1002, 1003, 1004]", true);
        nextPage = browserPage(nextPage, "[1005, 1006, 1007, 1008]", true);
        nextPage = browserPage(nextPage, "[1009, 1010]", false);
    }

    private Pageable browserPage(Pageable nextPage, String expectedProductIds, boolean expectsNextPage) {
        Page<ProductEntity> productPage = repository.findAll(nextPage);
        assertEquals(expectedProductIds, productPage.getContent().stream().map(p -> p.getId()).collect(Collectors.toList()).toString());
        assertEquals(expectsNextPage, productPage.hasNext());
        return productPage.nextPageable();
    }

    private void assertEqualsProduct(ProductEntity expectedEntity, ProductEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getName(), actualEntity.getName());
    }
}
