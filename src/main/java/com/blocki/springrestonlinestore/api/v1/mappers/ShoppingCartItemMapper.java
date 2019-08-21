package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingCartItemMapper {

    ShoppingCartItemMapper INSTANCE = Mappers.getMapper(ShoppingCartItemMapper.class);

    @Mapping(source = "product", target = "productDTO")
    @Mapping(source = "shoppingCart", target = "shoppingCartDTO")
    ShoppingCartItemDTO ShoppingCartItemToShoppingCartItemDTO(ShoppingCartItem shoppingCartItem);

    @Mapping(source = "productDTO", target = "product")
    @Mapping(source = "shoppingCartDTO", target = "shoppingCart")
    ShoppingCartItem ShoppingCartItemDTOToShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO);
}
