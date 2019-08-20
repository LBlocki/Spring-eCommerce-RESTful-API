package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.ShoppingCartDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShoppingCartMapper {

    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    ShoppingCartDTO shoppingCartToShoppingCartDTO(ShoppingCart shoppingCart);

    ShoppingCart shoppingCartDTOToShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
