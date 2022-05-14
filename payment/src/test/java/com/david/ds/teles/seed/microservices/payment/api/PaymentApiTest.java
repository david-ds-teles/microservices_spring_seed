package com.david.ds.teles.seed.microservices.payment.api;

import com.david.ds.teles.seed.microservices.clients.product.ProductClient;
import com.david.ds.teles.seed.microservices.clients.product.dto.ProductDTO;
import com.david.ds.teles.seed.microservices.payment.config.TestContainersConfig;
import com.david.ds.teles.seed.microservices.payment.dto.PaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PaymentApiTest extends TestContainersConfig {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ProductClient productClient;

    private List<String> validProductIds = List.of("1", "2");

    @BeforeEach
    void configuration() {
        List<ProductDTO> products = List.of(new ProductDTO("1", "product1", BigDecimal.TEN), new ProductDTO("2", "product2", BigDecimal.ONE));
        when(productClient.fetchAllById(validProductIds)).thenReturn(products);
    }

    @Test
    void payment() {
        //given
        PaymentDTO validPaymentDto = new PaymentDTO(3333, List.of("1", "2"));
        PaymentDTO invalidCard = new PaymentDTO(-1, List.of("1", "2"));
        PaymentDTO expiredCard = new PaymentDTO(1111, List.of("1", "2"));
        PaymentDTO rejectedCard = new PaymentDTO(2222, List.of("1", "2"));
        PaymentDTO invalidProducts = new PaymentDTO(3333, List.of());

        // when
        WebTestClient.BodyContentSpec response = postPaymentAndCheckStatus(validPaymentDto, HttpStatus.OK);

        // then
        response
                .jsonPath("$.status").isEqualTo("PAID")
                .jsonPath("$.value").isEqualTo(BigDecimal.valueOf(11));

        response = postPaymentAndCheckStatus(invalidCard, HttpStatus.BAD_REQUEST);
        response.jsonPath("$.message").isEqualTo("invalid credit card number: " + invalidCard.cardNumber());

        response = postPaymentAndCheckStatus(expiredCard, HttpStatus.BAD_REQUEST);
        response.jsonPath("$.message").isEqualTo("credit card expired: " + expiredCard.cardNumber());

        response = postPaymentAndCheckStatus(rejectedCard, HttpStatus.BAD_REQUEST);
        response.jsonPath("$.message").isEqualTo("credit card rejected: " + rejectedCard.cardNumber());

        response = postPaymentAndCheckStatus(invalidProducts, HttpStatus.BAD_REQUEST);
        response.jsonPath("$.message").isEqualTo("products invalid");

    }

    private WebTestClient.BodyContentSpec postPaymentAndCheckStatus(PaymentDTO body, HttpStatus expectedStatus) {
        return client.post().uri("/payment").body(BodyInserters.fromValue(body))
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

}
