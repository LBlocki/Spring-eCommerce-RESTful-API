package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ShoppingCartController.SHOPPING_CARTS_BASIC_URL)
public class ShoppingCartController {

    public static final String SHOPPING_CARTS_BASIC_URL = "/api/v1/shoppingCarts";

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ShoppingCartDTO> getShoppingCartById(@PathVariable Long id) {

        return shoppingCartService.getShoppingCartById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShoppingCartById(@PathVariable Long id) {

        shoppingCartService.deleteShoppingCartById(id);
    }

    @PostMapping("/{id}/actions/purchase")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ShoppingCartDTO> createPurchaseRequest(@PathVariable Long id) {

       //todo create service
        return null;
    }

    @PostMapping("/{id}/actions/cancel")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ShoppingCartDTO> createCancellationRequest(@PathVariable Long id) {

        //todo create service
        return null;
    }


    @GetMapping("/{id}/shoppingCartItems")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ShoppingCartItemDTO> getAllShoppingCartItems(@PathVariable Long id) {

       //todo create service
        return null;
    }

    @PostMapping("/{id}/shoppingCartItems")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ShoppingCartItemDTO> createNewShoppingCartItem(@PathVariable Long id) {

       //todo create service
        return null;
    }
}
