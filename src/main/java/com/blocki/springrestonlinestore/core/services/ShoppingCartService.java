package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartListDTO;

public interface ShoppingCartService {

    ShoppingCartListDTO getAllShoppingCarts();

    ShoppingCartDTO getShoppingCartById(Long id);

    ShoppingCartDTO saveShoppingCart(ShoppingCartDTO shoppingCartDTO);

    ShoppingCartDTO createNewShoppingCart(ShoppingCartDTO shoppingCartDTO);

    ShoppingCartDTO updateShoppingCart(Long id, ShoppingCartDTO shoppingCartDTO);

    ShoppingCartDTO patchShoppingCart(Long id, ShoppingCartDTO shoppingCartDTO);

    void deleteShoppingCartById(Long id);
}
