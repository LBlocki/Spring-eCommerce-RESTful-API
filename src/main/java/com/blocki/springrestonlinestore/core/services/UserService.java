package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.*;

public interface UserService {

    UserListDTO getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO saveUser(UserDTO userDTO);

    UserDTO createNewUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    UserDTO patchUser(Long id, UserDTO userDTO);

    void deleteUserById(Long id);

    ShoppingCartDTO createNewShoppingCart(Long id, ShoppingCartDTO shoppingCartDTO);

    ProductDTO createNewProduct(Long id, ProductDTO productDTO);

    ProductListDTO getAllProducts(Long id);

    ShoppingCartListDTO getAllShoppingCarts(Long id);

}
