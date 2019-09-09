package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
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

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() + ":(constructor):Injected product service:\n"
                    + productService.toString() + "\n");
        }

        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<ProductDTO>> getProductById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() + ":(getProductById): ID value in path: " + id  + "\n");
        }

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<Resource<ProductDTO>> getProductByName(@PathVariable final String productName) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() +
                    ":(getProductByName): Name value in path: " + productName  + "\n");
        }

        return ResponseEntity.ok(productService.getProductByName(productName));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Resource<ProductDTO>> patchProduct(@PathVariable final Long id,
                                                             @RequestBody @Valid final ProductDTO productDTO) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() +
                    ":(patchProduct): Id value in path: " + id + "," +
                    " Product passed in path:" + productDTO.toString() + "\n");
        }

        return ResponseEntity.ok(productService.patchProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() + ":(deleteProductById): Id value in path: " + id + "\n");
        }

        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }
}
