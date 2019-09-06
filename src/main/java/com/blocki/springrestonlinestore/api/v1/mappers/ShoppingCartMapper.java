package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShoppingCartMapper {

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(source = "shoppingCartItems",  target = "shoppingCartItemDTOs")
    })
    abstract public ShoppingCartDTO shoppingCartToShoppingCartDTO(ShoppingCart shoppingCart);

    @InheritInverseConfiguration
    abstract public ShoppingCart shoppingCartDTOToShoppingCart(ShoppingCartDTO shoppingCartDTO);

    @AfterMapping
    void setAdditionalShoppingCartDTOParameters(ShoppingCart shoppingCart, @MappingTarget
            ShoppingCartDTO shoppingCartDTO) {

        shoppingCartDTO.setUserDTO(userConverter.userToUserDTO(shoppingCart.getUser()));
        shoppingCartDTO.setUserDTOId(shoppingCart.getUser().getId());

        int i = 0;

        for(ShoppingCartItemDTO shoppingCartItemDTO : shoppingCartDTO.getShoppingCartItemDTOs()) {

            shoppingCartItemDTO.setShoppingCartDTO(shoppingCartDTO);
            shoppingCartItemDTO.setProductDTO(productConverter.productToProductDTO(
                    shoppingCart.getShoppingCartItems().get(i).getProduct()));

            i++;
        }
    }

    @AfterMapping
    void setAdditionalShoppingCartParameters(ShoppingCartDTO shoppingCartDTO, @MappingTarget
            ShoppingCart shoppingCart) {

        shoppingCart.setUser(userConverter.userDTOToUser(shoppingCartDTO.getUserDTO()));

        int i = 0;

        for( ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItems()) {

            shoppingCartItem.setShoppingCart(shoppingCart);
            shoppingCartItem.setProduct(productConverter.productDTOToProduct(
                    shoppingCartDTO.getShoppingCartItemDTOs().get(i).getProductDTO()));

            i++;
        }
    }
}
