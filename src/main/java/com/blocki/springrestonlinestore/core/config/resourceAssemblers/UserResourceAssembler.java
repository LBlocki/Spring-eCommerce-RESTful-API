package com.blocki.springrestonlinestore.core.config.resourceAssemblers;

import com.blocki.springrestonlinestore.api.v1.controllers.UserController;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler  implements ResourceAssembler<UserDTO, Resource<UserDTO>> {

    @Override
    public Resource<UserDTO> toResource(UserDTO userDTO) {
        return new Resource<>(userDTO,
                linkTo(methodOn(UserController.class).getUserById(userDTO.getId())).withSelfRel().withType("GET"),
                linkTo(methodOn(UserController.class).getUserByUsername(userDTO.getUsername()))
                        .withRel("get user by username").withType("GET"),
                linkTo(methodOn(UserController.class).getAllUsersProducts(userDTO.getId()))
                        .withRel("get list of user's products").withType("GET"),
                linkTo(methodOn(UserController.class).getShoppingCart(userDTO.getId()))
                        .withRel("get user's shopping cart").withType("GET"),
                linkTo(methodOn(UserController.class).addNewProductToUser(userDTO.getId(), new ProductDTO()))
                        .withRel("create product").withType("POST"),
                linkTo(methodOn(UserController.class).addNewShoppingCart(userDTO.getId(), new ShoppingCartDTO()))
                        .withRel("create shopping cart").withType("POST"),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("get list of users").withType("GET"));
    }
}
