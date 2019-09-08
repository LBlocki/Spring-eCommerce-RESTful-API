package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(ProductController.PRODUCTS_BASIC_URL)
public class ProductController {

    public static final String PRODUCTS_BASIC_URL = "/api/v1/products";

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
    @ResponseStatus(HttpStatus.OK)
    public Resource<ProductDTO> getProductById(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() + ":(getProductById): ID value in path: " + id  + "\n");
        }

        return productService.getProductById(id);
    }

    @GetMapping("/name/{productName}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ProductDTO> getProductByName(@PathVariable String productName) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() +
                    ":(getProductByName): Name value in path: " + productName  + "\n");
        }

        return productService.getProductByName(productName);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ProductDTO> patchProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() +
                    ":(patchProduct): Id value in path: " + id + "," +
                    " Product passed in path:" + productDTO.toString() + "\n");
        }

        return productService.patchProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() + ":(deleteProductById): Id value in path: " + id + "\n");
        }

        productService.deleteProductById(id);
    }
}
