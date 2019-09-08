package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(UserController.USER_CONTROLLER_BASIC_URL)
public class UserController {

    public static final String USER_CONTROLLER_BASIC_URL = "/api/v1/users";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() + ":(constructor):Injected user service:\n"
                    + userService.toString() + "\n");
        }

        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<UserDTO>> getAllUsers() {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName()
                    + ":(getAllUsers):Running method.\n");
        }

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDTO> getUserById(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() + ":(getUserById): ID value in path: " + id  + "\n");
        }

        return userService.getUserById(id);
    }

    @GetMapping("/name/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDTO> getUserByUsername(@PathVariable String username) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() +
                    ":(getUserByUsername): Name value in path: " + username  + "\n");
        }

        return userService.getUserByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<UserDTO> createNewUser(@RequestBody @Valid UserDTO userDTO) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() +
                    ":(createNewUser):User passed in path:" + userDTO.toString() + "\n");
        }

       return userService.createNewUser(userDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName() +
                    ":(updateUser): Id value in path: " + id + "," +
                    " User passed in path:" + userDTO.toString() + "\n");
        }

        return userService.updateUser(id, userDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<UserDTO> patchUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName() +
                    ":(patchUser): Id value in path: " + id + "," +
                    " User passed in path:" + userDTO.toString() + "\n");
        }

        return userService.patchUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() + ":(deleteUserById): ID value in path: " + id  + "\n");
        }

        userService.deleteUserById(id);
    }

    @GetMapping("/{id}/shoppingCart")
    @ResponseStatus(HttpStatus.OK)
    public  Resource<ShoppingCartDTO> getShoppingCart(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() + ":(getShoppingCart): ID value in path: " + id  + "\n");
        }

        return userService.getShoppingCartById(id);
    }

    @PostMapping("/{id}/shoppingCart")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ShoppingCartDTO> addNewShoppingCart(@PathVariable Long id,
                                                        @RequestBody @Valid  ShoppingCartDTO shoppingCartDTO) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() +
                    ":(addNewShoppingCart): Id value in path: " + id + "," +
                    " shoppingCart passed in path:" + shoppingCartDTO.toString() + "\n");
        }

       return userService.createNewShoppingCart(id, shoppingCartDTO);
    }

    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public  Resources<Resource<ProductDTO>> getAllUsersProducts(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName()
                    + ":(getAllUsersProducts):Running method.\n");
        }

        return userService.getAllProducts(id);
    }

    @PostMapping("/{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<ProductDTO> addNewProductToUser(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() +
                    ":(addNewProductToUser): Id value in path: " + id + "," +
                    " product passed in path:" + productDTO.toString() + "\n");
        }

       return userService.createNewProduct(id, productDTO);
    }
}
