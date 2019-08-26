package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//todo still needs validation
@RestController
@RequestMapping(UserController.USER_CONTROLLER_BASIC_URL)
public class UserController {

    public static final String USER_CONTROLLER_BASIC_URL = "/api/v1/users";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<UserDTO>> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDTO> getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<UserDTO> createNewUser(@RequestBody @Valid UserDTO userDTO) {

       return userService.createNewUser(userDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        return userService.updateUser(id, userDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDTO> patchUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        return userService.patchUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id) {

        userService.deleteUserById(id);
    }

    @GetMapping("/{id}/shoppingCarts")
    @ResponseStatus(HttpStatus.OK)
    public  Resources<Resource<ShoppingCartDTO>> getAllShoppingCarts(@PathVariable Long id) {

        return userService.getAllShoppingCarts(id);
    }

    @PostMapping("/{id}/shoppingCarts")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ShoppingCartDTO> addNewShoppingCart(@PathVariable Long id,  @RequestBody @Valid  ShoppingCartDTO shoppingCartDTO) {

       return userService.createNewShoppingCart(id, shoppingCartDTO);
    }

    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public  Resources<Resource<ProductDTO>> getAllUsersProducts(@PathVariable Long id) {

        return userService.getAllProducts(id);
    }

    @PostMapping("/{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ProductDTO> addNewProductToUser(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {

       return userService.createNewProduct(id, productDTO);
    }
}
