package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShoppingCartMapper {

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "shoppingCartItems",  target = "shoppingCartItemDTOs"),
            @Mapping(source = "user", target = "userDTO")
    })
    abstract public ShoppingCartDTO shoppingCartToShoppingCartDTO(ShoppingCart shoppingCart);

    @InheritInverseConfiguration
    abstract public ShoppingCart shoppingCartDTOToShoppingCart(ShoppingCartDTO shoppingCartDTO);

    @AfterMapping
    protected void setAdditionalShoppingCartDTOParameters(ShoppingCart shoppingCart, @MappingTarget
            ShoppingCartDTO shoppingCartDTO) {

        if(shoppingCart.getUser() == null) {

            throw new NotFoundException("Shopping cart's user is null");
        }

        else if(shoppingCartDTO.getShoppingCartItemDTOs() == null) {

            throw new NotFoundException("Shopping cart item field is null");
        }

        shoppingCartDTO.setUserDTO(userConverter.userToUserDTO(shoppingCart.getUser()));
        shoppingCartDTO.setUserDTOId(shoppingCart.getUser().getId());

        for(ShoppingCartItemDTO shoppingCartItemDTO : shoppingCartDTO.getShoppingCartItemDTOs()) {

            shoppingCartItemDTO.setShoppingCartDTO(shoppingCartDTO);
        }
    }

    @AfterMapping
    protected void setAdditionalShoppingCartParameters(ShoppingCartDTO shoppingCartDTO, @MappingTarget
            ShoppingCart shoppingCart) {

        if(shoppingCartDTO.getUserDTO() == null) {

            throw new NotFoundException("Shopping cart's user not found");
        }

        else if (shoppingCartDTO.getShoppingCartItemDTOs() == null) {

            throw new NotFoundException("Shopping cart item field is null");
        }

        for( ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItems()) {

            shoppingCartItem.setShoppingCart(shoppingCart);
        }
    }
}
