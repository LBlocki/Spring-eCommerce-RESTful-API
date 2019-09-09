package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

public interface ShoppingCartService {

    Resource<ShoppingCartDTO> getShoppingCartById(Long id);

    void deleteShoppingCartById(Long id);

    Resource<ShoppingCartDTO> createPurchaseRequest(Long id);

    Resource<ShoppingCartItemDTO> createNewShoppingCartItem(Long id, ShoppingCartItemDTO shoppingCartItemDTO);

    Resources<Resource<ShoppingCartItemDTO>> getAllShoppingCartItems(Long id);
}
