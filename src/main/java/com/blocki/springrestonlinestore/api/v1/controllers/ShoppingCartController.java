package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.services.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(ShoppingCartController.SHOPPING_CARTS_BASIC_URL)
public class ShoppingCartController {

    public static final String SHOPPING_CARTS_BASIC_URL = "/api/v1/shoppingCarts";

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName() + ":(constructor):Injected shopping cat service:\n"
                    + shoppingCartService.toString() + "\n");
        }

        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<ShoppingCartDTO> getShoppingCartById(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(getShoppingCartById): ID value in path: " + id  + "\n");
        }

        return shoppingCartService.getShoppingCartById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShoppingCartById(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(deleteShoppingCartById): Id value in path: " + id + "\n");
        }

        shoppingCartService.deleteShoppingCartById(id);
    }

    @PostMapping("/{id}/actions/purchase")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ShoppingCartDTO> createPurchaseRequest(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(createPurchaseRequest): Id value in path: " + id + "\n");
        }

      return shoppingCartService.createPurchaseRequest(id);
    }

    @PostMapping("/{id}/actions/cancel")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCancellationRequest(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(createCancellationRequest): Id value in path: " + id + "\n");
        }

        shoppingCartService.createCancellationRequest(id);
    }


    @GetMapping("/{id}/shoppingCartItems")
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<ShoppingCartItemDTO>> getAllShoppingCartItems(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(getAllShoppingCartItems): Id value in path: " + id + "\n");
        }

       return shoppingCartService.getAllShoppingCartItems(id);
    }

    @PostMapping("/{id}/shoppingCartItems")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ShoppingCartItemDTO> createNewShoppingCartItem(
            @PathVariable Long id, @RequestBody @Valid ShoppingCartItemDTO shoppingCartItemDTO) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName() +
                    ":(createNewShoppingCartItem): Id value in path: " + id + "," +
                    " Shopping Cart item passed in path:" + shoppingCartItemDTO.toString() + "\n");
        }

       return shoppingCartService.createNewShoppingCartItem(id, shoppingCartItemDTO);
    }
}
