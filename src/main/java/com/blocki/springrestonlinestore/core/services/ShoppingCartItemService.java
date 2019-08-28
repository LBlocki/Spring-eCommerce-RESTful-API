package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import org.springframework.hateoas.Resource;

public interface ShoppingCartItemService {

    Resource<ShoppingCartItemDTO> getShoppingCartItemById(Long id);

    void deleteShoppingCartItemById(Long id);
}
