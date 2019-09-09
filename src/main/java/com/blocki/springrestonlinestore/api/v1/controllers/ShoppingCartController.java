package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.services.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping(value = ShoppingCartController.SHOPPING_CARTS_BASIC_URL, produces = "application/hal+json")
public class ShoppingCartController {

    static final String SHOPPING_CARTS_BASIC_URL = "/api/v1/shoppingCarts";

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName() + ":(constructor):Injected shopping cart service:\n"
                    + shoppingCartService.toString() + "\n");
        }

        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<ShoppingCartDTO>> getShoppingCartById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(getShoppingCartById): ID value in path: " + id  + "\n");
        }

        return ResponseEntity.ok(shoppingCartService.getShoppingCartById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShoppingCartById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(deleteShoppingCartById): Id value in path: " + id + "\n");
        }

        shoppingCartService.deleteShoppingCartById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/actions/purchase")
    public ResponseEntity<Resource<ShoppingCartDTO>> createPurchaseRequest(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(createPurchaseRequest): Id value in path: " + id + "\n");
        }

        final Resource<ShoppingCartDTO> shoppingCartDTOResource = shoppingCartService.createPurchaseRequest(id);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}/actions/purchase")
                        .buildAndExpand(shoppingCartDTOResource.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(shoppingCartDTOResource);
    }

    @GetMapping("/{id}/shoppingCartItems")
    public ResponseEntity<Resources<Resource<ShoppingCartItemDTO>>> getAllShoppingCartItems(
            @PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName()
                    + ":(getAllShoppingCartItems): Id value in path: " + id + "\n");
        }

       return ResponseEntity.ok(shoppingCartService.getAllShoppingCartItems(id));
    }

    @PostMapping("/{id}/shoppingCartItems")
    public ResponseEntity<Resource<ShoppingCartItemDTO>> createNewShoppingCartItem(
            @PathVariable final Long id, @RequestBody @Valid final ShoppingCartItemDTO shoppingCartItemDTO) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName() +
                    ":(createNewShoppingCartItem): Id value in path: " + id + "," +
                    " Shopping Cart item passed in path:" + shoppingCartItemDTO.toString() + "\n");
        }

        final Resource<ShoppingCartItemDTO> shoppingCartItemDTOResource =
                shoppingCartService.createNewShoppingCartItem(id, shoppingCartItemDTO);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}/shoppingCartItems")
                .buildAndExpand(shoppingCartItemDTOResource.getId())
                .toUri();

        return ResponseEntity.created(uri).body(shoppingCartItemDTOResource);
    }
}
