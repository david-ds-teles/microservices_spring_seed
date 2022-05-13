package com.david.ds.teles.seed.microservices.product.api;

import com.david.ds.teles.seed.microservices.product.api.specs.ProductApiSpec;
import com.david.ds.teles.seed.microservices.product.dto.ProductDTO;
import com.david.ds.teles.seed.microservices.product.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductApi implements ProductApiSpec {

    private ProductService service;

    @Override
    public ResponseEntity<ProductDTO> fetch(String id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<List<ProductDTO>> fetchAllById(List<String> products) {
        List<ProductDTO> result = service.findAllById(products);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product) {
        ProductDTO result = this.service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Void> update(String id, ProductDTO product) {
        this.service.update(id, product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
