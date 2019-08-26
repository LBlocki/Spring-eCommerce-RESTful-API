package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.services.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = ShoppingCartItemController.SHOPPING_CART_ITEMS_BASIC_URL)
public class ShoppingCartItemController {

    public static final String SHOPPING_CART_ITEMS_BASIC_URL = "/api/v1/shoppingCartItems";

    private final ShoppingCartItemService shoppingCartItemService;

    @Autowired
    public ShoppingCartItemController(ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartItemService = shoppingCartItemService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ShoppingCartItemDTO> getShoppingCartById(@PathVariable Long id) {

        return shoppingCartItemService.getShoppingCartItemById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShoppingCartById(@PathVariable Long id) {

        shoppingCartItemService.getShoppingCartItemById(id);
    }
}
