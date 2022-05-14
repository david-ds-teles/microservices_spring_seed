package com.david.ds.teles.seed.microservices.payment.service;

import com.david.ds.teles.seed.microservices.clients.product.dto.ProductDTO;
import com.david.ds.teles.seed.microservices.utils.exceptions.MyExceptionError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.util.logging.Level.FINE;

/**
 * Using feign client now. This is just for future reference
 */
@Deprecated(forRemoval = true)@Service
@Slf4j
public class ProductServiceIntegrationImpl implements ProductIntegrationService {

    private final WebClient webClient;

    private String productMicroserviceUrl;

    public ProductServiceIntegrationImpl(WebClient.Builder webClient, @Value("${microservices.product}") String productMicroservice) {
        this.webClient = webClient.build();
        this.productMicroserviceUrl = "http://" + productMicroservice;
    }

    @Override
    public Flux<ProductDTO> findAllById(List<String> productsId) {
        log.info("starting call to product microservices");

        String ids = productsId.toString().replace("[", "").replace("]", "");

        return webClient.get()
                .uri(productMicroserviceUrl + "/product/all/" + ids)
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .log(log.getName(), FINE)
                .onErrorMap(WebClientResponseException.class, ex -> webClientRspErrorHandler(ex));
    }

    private Throwable webClientRspErrorHandler(WebClientResponseException ex) {

        switch (ex.getStatusCode()) {

            case NOT_FOUND:
                return new MyExceptionError("Not Found", 404);

            case BAD_REQUEST:
                return new MyExceptionError("Invalid Data", 400);

            default:
                log.warn("Unexpected HTTP error: {}", ex);
                return ex;
        }
    }
}
