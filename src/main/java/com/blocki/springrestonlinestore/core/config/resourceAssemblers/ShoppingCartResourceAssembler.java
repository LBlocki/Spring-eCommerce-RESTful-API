package com.blocki.springrestonlinestore.core.config.resourceAssemblers;



import com.blocki.springrestonlinestore.api.v1.controllers.ShoppingCartController;
import com.blocki.springrestonlinestore.api.v1.controllers.UserController;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ShoppingCartResourceAssembler  implements ResourceAssembler<ShoppingCartDTO, Resource<ShoppingCartDTO>> {

    @Override
    public Resource<ShoppingCartDTO> toResource(ShoppingCartDTO shoppingCartDTO) {

        return new Resource<>(shoppingCartDTO,
                linkTo(methodOn(ShoppingCartController.class).getShoppingCartById(shoppingCartDTO.getId()))
                        .withSelfRel(),
                linkTo(methodOn(UserController.class).getShoppingCart(shoppingCartDTO.getUserDTO().getId()))
                        .withRel("user shopping_cart"),
                linkTo(methodOn(ShoppingCartController.class).getAllShoppingCartItems(shoppingCartDTO.getId()))
                        .withRel("shopping_cart items"),
                linkTo(methodOn(UserController.class).getUserById(shoppingCartDTO.getUserDTOId()))
                        .withRel("shopping cart's user"));
    }
}
