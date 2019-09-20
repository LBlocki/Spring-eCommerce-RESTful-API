package com.blocki.springecommerceapp.api.v1.controllers;

import com.blocki.springecommerceapp.api.v1.models.ProductDTO;
import com.blocki.springecommerceapp.core.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = ProductController.PRODUCTS_BASIC_URL, produces = "application/hal+json")
public class ProductController {

    static final String PRODUCTS_BASIC_URL = "/api/v1/products";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<ProductDTO>> getProductById(@PathVariable final Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<Resource<ProductDTO>> getProductByName(@PathVariable final String productName) {

        return ResponseEntity.ok(productService.getProductByName(productName));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<ProductDTO>> patchProduct(@PathVariable final Long id,
                                                             @RequestBody @Valid final ProductDTO productDTO) {

        return ResponseEntity.ok(productService.patchProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable final Long id) {

        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }
}
