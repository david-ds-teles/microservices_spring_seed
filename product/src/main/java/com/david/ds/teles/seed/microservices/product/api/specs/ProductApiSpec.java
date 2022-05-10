package com.david.ds.teles.seed.microservices.product.api.specs;

import com.david.ds.teles.seed.microservices.product.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "A product api")
public interface ProductApiSpec {

    @Operation(
            summary = "Fetch by product ID",
            description = "Fetch product information using a product ID code"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "${api.rsp.ok}"),
                    @ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
                    @ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> fetch(@PathVariable("id") String id);

    @Operation(
            summary = "Fetch all products by the given IDs",
            description = "Fetch all products information using a product ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "${api.rsp.ok}"),
                    @ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
                    @ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
            }
    )
    @GetMapping("/all/{products}")
    public ResponseEntity<List<ProductDTO>> fetchAllById(@PathVariable("products")List<String> products);

    @Operation(
            summary = "Create Product",
            description = "Create a new product using provided information"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "${api.rsp.ok}"),
                    @ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
            }
    )
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product);

    @Operation(
            summary = "Update Product",
            description = "Update a new product using provided information"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "${api.rsp.ok}"),
                    @ApiResponse(responseCode = "404", description = "${api.rsp.notFound}"),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody ProductDTO product);

    @Operation(
            summary = "Delete Product",
            description = "Delete a product using provided id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "400", description = "${api.rsp.badRequest}"),
                    @ApiResponse(responseCode = "404", description = "${api.rsp.notFound}")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id);
}
