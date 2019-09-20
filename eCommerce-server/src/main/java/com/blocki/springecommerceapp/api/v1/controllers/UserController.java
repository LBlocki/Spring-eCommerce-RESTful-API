package com.blocki.springecommerceapp.api.v1.controllers;

import com.blocki.springecommerceapp.api.v1.models.OrderDTO;
import com.blocki.springecommerceapp.api.v1.models.ProductDTO;
import com.blocki.springecommerceapp.api.v1.models.UserDTO;
import com.blocki.springecommerceapp.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping( value = UserController.USER_CONTROLLER_BASIC_URL, produces = "application/hal+json")
public class UserController {

    static final String USER_CONTROLLER_BASIC_URL = "/api/v1/users";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Resources<Resource<UserDTO>>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<UserDTO>> getUserById(@PathVariable final Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<Resource<UserDTO>> getUserByUsername(@PathVariable final String username) {

        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Resource<UserDTO>> createNewUser(@RequestBody @Valid final UserDTO userDTO) {

        final Resource<UserDTO> userDTOResource = userService.createNewUser(userDTO);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .buildAndExpand(userDTOResource.getId())
                .toUri();

        return ResponseEntity.created(uri).body(userDTOResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource<UserDTO>> updateUser(@PathVariable final Long id,
                                                        @RequestBody @Valid final UserDTO userDTO) {

        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Resource<UserDTO>> patchUser(@PathVariable final Long id,
                                                       @RequestBody @Valid final UserDTO userDTO) {

        return ResponseEntity.ok(userService.patchUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable final Long id) {

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/order")
    public  ResponseEntity<Resource<OrderDTO>> getOrder(@PathVariable final Long id) {

        return ResponseEntity.ok(userService.getOrderById(id));
    }

    @PostMapping("/{id}/order")
    public ResponseEntity<Resource<OrderDTO>> addNewOrder(@PathVariable final Long id,
                                                                 @RequestBody @Valid final OrderDTO orderDTO) {

        final Resource<OrderDTO> orderDTOResource =
                userService.createNewOrder(id, orderDTO);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}/order")
                .buildAndExpand(orderDTOResource.getId())
                .toUri();

        return ResponseEntity.created(uri).body(orderDTOResource);

    }

    @GetMapping("/{id}/products")
    public  ResponseEntity<Resources<Resource<ProductDTO>>> getAllUsersProducts(@PathVariable final Long id) {

        return ResponseEntity.ok(userService.getAllProducts(id));
    }

    @PostMapping("/{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Resource<ProductDTO>> addNewProductToUser(@PathVariable final Long id,
                                                                    @RequestBody @Valid final ProductDTO productDTO) {

        final Resource<ProductDTO> productDTOResource =
                userService.createNewProduct(id, productDTO);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}/products")
                .buildAndExpand(productDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(productDTOResource);

    }
}
