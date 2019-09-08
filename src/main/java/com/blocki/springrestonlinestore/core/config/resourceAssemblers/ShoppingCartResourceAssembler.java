package com.blocki.springrestonlinestore.core.config.resourceAssemblers;



import com.blocki.springrestonlinestore.api.v1.controllers.ShoppingCartController;
import com.blocki.springrestonlinestore.api.v1.controllers.UserController;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
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
                        .withSelfRel().withType("GET"),
                linkTo(methodOn(UserController.class).getShoppingCart(shoppingCartDTO.getUserDTO().getId()))
                        .withRel("get user shopping_cart").withType("GET"),
                linkTo(methodOn(ShoppingCartController.class).
                        createNewShoppingCartItem(shoppingCartDTO.getId(), new ShoppingCartItemDTO()))
                        .withRel("create shopping cart item").withType("POST"),
                linkTo(methodOn(ShoppingCartController.class).createPurchaseRequest(shoppingCartDTO.getId()))
                        .withRel("create purchase request").withType("POST"),
                linkTo(methodOn(ShoppingCartController.class).getShoppingCartById(shoppingCartDTO.getId()))
                        .withRel("create cancellation request").withType("POST"),
                linkTo(methodOn(ShoppingCartController.class).getShoppingCartById(shoppingCartDTO.getId()))
                        .withRel("delete shopping cart").withType("DELETE"),
                linkTo(methodOn(UserController.class).getUserById(shoppingCartDTO.getUserDTOId()))
                        .withRel("get shopping cart's user").withType("GET"),
                linkTo(methodOn(ShoppingCartController.class).getAllShoppingCartItems(shoppingCartDTO.getId()))
                        .withRel("get list of shopping_cart items").withType("GET"));
    }
}
