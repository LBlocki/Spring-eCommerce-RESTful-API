package com.blocki.springrestonlinestore.core.config.resourceAssemblers;

import com.blocki.springrestonlinestore.api.v1.controllers.ShoppingCartController;
import com.blocki.springrestonlinestore.api.v1.controllers.ShoppingCartItemController;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ShoppingCartItemResourceAssembler  implements ResourceAssembler<ShoppingCartItemDTO,
        Resource<ShoppingCartItemDTO>> {

    @Override
    public Resource<ShoppingCartItemDTO> toResource(ShoppingCartItemDTO shoppingCartItemDTO) {

        return new Resource<>(shoppingCartItemDTO,
                linkTo(methodOn(ShoppingCartItemController.class).getShoppingCartById(shoppingCartItemDTO.getId()))
                        .withSelfRel().withType("GET"),
                linkTo(methodOn(ShoppingCartItemController.class).getShoppingCartById(shoppingCartItemDTO.getId()))
                        .withRel("delete shopping_cart item").withType("DELETE"),
                linkTo(methodOn(ShoppingCartController.class).getAllShoppingCartItems(shoppingCartItemDTO
                        .getShoppingCartDTO().getId())).withRel("get list of shopping_cart items").withType("GET"));

    }
}
