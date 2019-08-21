package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingCartMapper {

    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "shoppingCartItems", target = "shoppingCartItemDTOs")
    ShoppingCartDTO shoppingCartToShoppingCartDTO(ShoppingCart shoppingCart);

    @Mapping(source = "userDTO", target = "user")
    @Mapping(source = "shoppingCartItemDTOs", target = "shoppingCartItems")
    ShoppingCart shoppingCartDTOToShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
