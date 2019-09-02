package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ProductController.PRODUCTS_BASIC_URL)
public class ProductController {

    public static final String PRODUCTS_BASIC_URL = "/api/v1/products";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ProductDTO> getProductById(@PathVariable Long id) {

        return productService.getProductById(id);
    }

    @GetMapping("/name/{productName}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ProductDTO> getProductByName(@PathVariable String productName) {

        return productService.getProductByName(productName);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ProductDTO> patchProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO userDTO) {

        return productService.patchProduct(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long id) {

        productService.deleteProductById(id);
    }
}
