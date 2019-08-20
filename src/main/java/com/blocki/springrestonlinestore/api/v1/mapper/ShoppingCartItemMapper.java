package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShoppingCartItemMapper {

    ShoppingCartItemMapper INSTANCE = Mappers.getMapper(ShoppingCartItemMapper.class);

    ShoppingCartItemDTO ShoppingCartItemToShoppingCartItemDTO(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem ShoppingCartItemDTOToShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO);
}
