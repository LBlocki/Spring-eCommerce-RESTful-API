package com.blocki.springecommerceapp.core.config.resourceAssemblers;

import com.blocki.springecommerceapp.api.v1.controllers.UserController;
import com.blocki.springecommerceapp.api.v1.models.OrderDTO;
import com.blocki.springecommerceapp.api.v1.models.ProductDTO;
import com.blocki.springecommerceapp.api.v1.models.UserDTO;
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
                linkTo(methodOn(UserController.class).getOrder(userDTO.getId()))
                        .withRel("get user's order").withType("GET"),
                linkTo(methodOn(UserController.class).addNewProductToUser(userDTO.getId(), new ProductDTO()))
                        .withRel("create product").withType("POST"),
                linkTo(methodOn(UserController.class).addNewOrder(userDTO.getId(), new OrderDTO()))
                        .withRel("create order").withType("POST"),
                linkTo(methodOn(UserController.class).updateUser(userDTO.getId(), new UserDTO()))
                        .withRel("update user").withType("PUT"),
                linkTo(methodOn(UserController.class).patchUser(userDTO.getId(), new UserDTO()))
                        .withRel("patch user").withType("PATCH"),
                linkTo(methodOn(UserController.class).getUserById(userDTO.getId()))
                        .withRel("delete user").withType("DELETE"),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("get list of users").withType("GET"));
    }
}
