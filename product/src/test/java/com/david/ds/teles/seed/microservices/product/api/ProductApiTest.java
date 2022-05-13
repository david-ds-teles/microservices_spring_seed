package com.david.ds.teles.seed.microservices.product.api;

import com.david.ds.teles.seed.microservices.product.config.TestContainersConfig;
import com.david.ds.teles.seed.microservices.product.data.entities.ProductEntity;
import com.david.ds.teles.seed.microservices.product.data.persistence.ProductRepository;
import com.david.ds.teles.seed.microservices.product.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductApiTest extends TestContainersConfig {

    private final String nonexistentProductId = "13";

    @Autowired
    private WebTestClient client;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setupDb() {
        repository.deleteAll();
    }

    @Test
    void should_fetch_a_product_by_its_id() {

        String productId = "1";

        postProductAndCheckStatus(productId, CREATED);

        assertTrue(repository.findById("1").isPresent());

        getProductAndCheckStatus(productId, null, OK).jsonPath("$.id").isEqualTo(productId);
        getProductAndCheckStatus(nonexistentProductId, null, NOT_FOUND).jsonPath("$.message").isEqualTo("no product found for id: " + nonexistentProductId);
        getProductAndCheckStatus(nonexistentProductId, "pt-BR",NOT_FOUND).jsonPath("$.message").isEqualTo("nenhum produto encontrado para id: " + nonexistentProductId);
    }

    @Test
    void should_fetch_all_products_by_its_id() {

        List<String> productsId = List.of("1", "2", "3");

        productsId.forEach(productId -> postProductAndCheckStatus(productId, CREATED));

        assertTrue(repository.findById("1").isPresent());
        assertTrue(repository.findById("2").isPresent());
        assertTrue(repository.findById("3").isPresent());

        getAllProductsAndCheckStatus(productsId, OK).jsonPath("$", hasSize(3));
    }

    @Test
    void should_update_a_product() {
        String productId = "1";

        postProductAndCheckStatus(productId, CREATED);

        assertTrue(repository.findById(productId).isPresent());

        ProductDTO dto = new ProductDTO(productId, 1, "updated", BigDecimal.TEN);

        updateProductAndCheckStatus(productId, dto, NO_CONTENT);

        ProductEntity entity = repository.findById("1").get();
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getName(), dto.name());

        updateProductAndCheckStatus(nonexistentProductId, dto, NOT_FOUND).jsonPath("$.message").isEqualTo("no product found for id: " + nonexistentProductId);
        ;
    }

    private WebTestClient.BodyContentSpec updateProductAndCheckStatus(String productId, ProductDTO dto, HttpStatus expectedStatus) {
        return client.put()
                .uri("/product/" + productId)
                .body(just(dto), ProductDTO.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    @Test
    void should_delete_a_product() {

        String productId = "1";

        postProductAndCheckStatus(productId, CREATED);
        assertTrue(repository.findById(productId).isPresent());

        deleteProductAndCheckStatus(productId, OK);
        assertFalse(repository.findById(productId).isPresent());

        deleteProductAndCheckStatus(nonexistentProductId, NOT_FOUND).jsonPath("$.message").isEqualTo("no product found for id: " + nonexistentProductId);
        ;
    }

    @Test
    void should_give_an_error_when_duplicate_id() {

        String productId = "1";

        postProductAndCheckStatus(productId, CREATED);

        assertTrue(repository.findById(productId).isPresent());

        postProductAndCheckStatus(productId, BAD_REQUEST)
                .jsonPath("$.message").isEqualTo("the provided id " + productId + " is duplicated");
    }

    private WebTestClient.BodyContentSpec getProductAndCheckStatus(String productIdPath, String lang, HttpStatus expectedStatus) {
        return client.get()
                .uri("/product/" + productIdPath)
                .accept(APPLICATION_JSON)
                .header("accept-language", lang == null ? "en" : lang)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec postProductAndCheckStatus(String productId, HttpStatus expectedStatus) {
        ProductDTO product = new ProductDTO(productId, "Name " + productId, BigDecimal.TEN);

        return client.post()
                .uri("/product")
                .body(just(product), ProductDTO.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec deleteProductAndCheckStatus(String id, HttpStatus expectedStatus) {
        return client.delete()
                .uri("/product/" + id)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec getAllProductsAndCheckStatus(List<String> productsId, HttpStatus expectedStatus) {
        String ids = productsId.toString().replace("[", "").replace("]", "");
        return client.get()
                .uri("/product/all/" + ids)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }
}
