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

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() + ":(constructor):Injected user service:\n"
                    + userService.toString() + "\n");
        }

        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Resources<Resource<UserDTO>>> getAllUsers() {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName()
                    + ":(getAllUsers):Running method.\n");
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<UserDTO>> getUserById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() + ":(getUserById): ID value in path: " + id  + "\n");
        }

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<Resource<UserDTO>> getUserByUsername(@PathVariable final String username) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() +
                    ":(getUserByUsername): Name value in path: " + username  + "\n");
        }

        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Resource<UserDTO>> createNewUser(@RequestBody @Valid final UserDTO userDTO) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() +
                    ":(createNewUser):User passed in path:" + userDTO.toString() + "\n");
        }

        final Resource<UserDTO> userDTOResource = userService.createNewUser(userDTO);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .buildAndExpand(userDTOResource.getId())
                .toUri();

        return ResponseEntity.created(uri).body(userDTOResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource<UserDTO>> updateUser(@PathVariable final Long id,
                                                        @RequestBody @Valid final UserDTO userDTO) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName() +
                    ":(updateUser): Id value in path: " + id + "," +
                    " User passed in path:" + userDTO.toString() + "\n");
        }

        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Resource<UserDTO>> patchUser(@PathVariable final Long id,
                                                       @RequestBody @Valid final UserDTO userDTO) {

        if(log.isDebugEnabled()) {

            log.debug(ShoppingCartController.class.getName() +
                    ":(patchUser): Id value in path: " + id + "," +
                    " User passed in path:" + userDTO.toString() + "\n");
        }

        return ResponseEntity.ok(userService.patchUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() + ":(deleteUserById): ID value in path: " + id  + "\n");
        }

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/shoppingCart")
    public  ResponseEntity<Resource<ShoppingCartDTO>> getShoppingCart(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() + ":(getShoppingCart): ID value in path: " + id  + "\n");
        }

        return ResponseEntity.ok(userService.getShoppingCartById(id));
    }

    @PostMapping("/{id}/shoppingCart")
    public ResponseEntity<Resource<ShoppingCartDTO>> addNewShoppingCart(@PathVariable final Long id,
                                                        @RequestBody @Valid final ShoppingCartDTO shoppingCartDTO) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() +
                    ":(addNewShoppingCart): Id value in path: " + id + "," +
                    " shoppingCart passed in path:" + shoppingCartDTO.toString() + "\n");
        }

        final Resource<ShoppingCartDTO> shoppingCartDTOResource =
                userService.createNewShoppingCart(id, shoppingCartDTO);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}/shoppingCart")
                .buildAndExpand(shoppingCartDTOResource.getId())
                .toUri();

        return ResponseEntity.created(uri).body(shoppingCartDTOResource);

    }

    @GetMapping("/{id}/products")
    public  ResponseEntity<Resources<Resource<ProductDTO>>> getAllUsersProducts(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName()
                    + ":(getAllUsersProducts):Running method.\n");
        }

        return ResponseEntity.ok(userService.getAllProducts(id));
    }

    @PostMapping("/{id}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Resource<ProductDTO>> addNewProductToUser(@PathVariable final Long id,
                                                                    @RequestBody @Valid final ProductDTO productDTO) {

        if(log.isDebugEnabled()) {

            log.debug(UserController.class.getName() +
                    ":(addNewProductToUser): Id value in path: " + id + "," +
                    " product passed in path:" + productDTO.toString() + "\n");
        }

        final Resource<ProductDTO> productDTOResource =
                userService.createNewProduct(id, productDTO);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}/products")
                .buildAndExpand(productDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(productDTOResource);

    }
}
