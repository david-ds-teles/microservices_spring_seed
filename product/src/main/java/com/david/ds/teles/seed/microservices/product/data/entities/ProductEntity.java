package com.david.ds.teles.seed.microservices.product.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class ProductEntity {

    @Id
    private String id;

    @Version
    private Integer version;

    private String name;

    private BigDecimal value;

    public ProductEntity(String id, String name, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
}
