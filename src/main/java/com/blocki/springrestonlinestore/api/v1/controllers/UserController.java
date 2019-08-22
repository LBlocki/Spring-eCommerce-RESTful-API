package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.*;
import com.blocki.springrestonlinestore.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserListDTO getListOfUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createNewUser(@RequestBody @Valid UserDTO userDTO) {

       return userService.createNewUser(userDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        return userService.updateUser(id, userDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO patchUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        return userService.patchUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id) {

        userService.deleteUserById(id);
    }

    @GetMapping("/{id}/shoppingCarts")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartListDTO getListOfAllShoppingCarts(@PathVariable Long id) {

        return userService.getAllShoppingCarts(id);
    }

    @PostMapping("/{id}/shoppingCarts")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDTO addNewShoppingCart(@PathVariable Long id,  @RequestBody @Valid  ShoppingCartDTO shoppingCartDTO) {

       return userService.createNewShoppingCart(id, shoppingCartDTO);
    }

    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getListOfAllUsersProducts(@PathVariable Long id) {

        return userService.getAllProducts(id);
    }

    @PostMapping("/{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addNewProductToUser(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {

       return userService.createNewProduct(id, productDTO);
    }
}
