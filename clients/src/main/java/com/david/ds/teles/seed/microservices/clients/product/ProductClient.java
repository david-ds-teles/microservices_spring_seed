package com.david.ds.teles.seed.microservices.clients.product;

import com.david.ds.teles.seed.microservices.clients.product.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "PRODUCT", path = "/product")
public interface ProductClient {

    @GetMapping("/{id}")
    ProductDTO fetch(@PathVariable("id") String id);

    @GetMapping("/all/{products}")
    List<ProductDTO> fetchAllById(@PathVariable("products") List<String> products);

    @PostMapping
    ProductDTO create(@RequestBody ProductDTO product);

    @PutMapping("/{id}")
    void update(@PathVariable("id") String id, @RequestBody ProductDTO product);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") String id);
}
