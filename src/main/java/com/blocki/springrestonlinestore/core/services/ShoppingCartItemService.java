package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemListDTO;

public interface ShoppingCartItemService {

    ShoppingCartItemListDTO getAllShoppingCartItems();

    ShoppingCartItemDTO getShoppingCartItemById(Long id);

    ShoppingCartItemDTO saveShoppingCartItem(ShoppingCartItemDTO userDTO);

    ShoppingCartItemDTO createNewShoppingCartItem(ShoppingCartItemDTO userDTO);

    void deleteShoppingCartItemById(Long id);
}
