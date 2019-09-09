package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.services.ShoppingCartItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = ShoppingCartItemController.SHOPPING_CART_ITEMS_BASIC_URL, produces = "application/hal+json")
public class ShoppingCartItemController {

    static final String SHOPPING_CART_ITEMS_BASIC_URL = "/api/v1/shoppingCartItems";

    private final ShoppingCartItemService shoppingCartItemService;

    @Autowired
    public ShoppingCartItemController(ShoppingCartItemService shoppingCartItemService) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartItemController.class.getName()
                    + ":(constructor):Injected shopping cart item service:\n"
                    + shoppingCartItemService.toString() + "\n");
        }

        this.shoppingCartItemService = shoppingCartItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<ShoppingCartItemDTO>> getShoppingCartById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartItemController.class.getName()
                    + ":(getShoppingCartById): ID value in path: " + id  + "\n");
        }

        return ResponseEntity.ok(shoppingCartItemService.getShoppingCartItemById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShoppingCartById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() + ":(deleteShoppingCartById): Id value in path: " + id + "\n");
        }

        shoppingCartItemService.getShoppingCartItemById(id);

        return ResponseEntity.noContent().build();
    }
}
