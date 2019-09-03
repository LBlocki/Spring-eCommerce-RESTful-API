package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShoppingCartItemMapper {

    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);

    abstract public ShoppingCartItemDTO ShoppingCartItemToShoppingCartItemDTO(ShoppingCartItem shoppingCartItem);

    abstract public ShoppingCartItem ShoppingCartItemDTOToShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO);

    @AfterMapping
    protected void setAdditionalShoppingCartItemDTOParameters(ShoppingCartItem shoppingCartItem,
                                                              @MappingTarget ShoppingCartItemDTO shoppingCartItemDTO) {

        if(shoppingCartItem.getShoppingCart() == null) {

            throw new NotFoundException("ShoppingCartItem's Shopping cart is null");
        }

        else if(shoppingCartItem.getProduct() == null) {

            throw new NotFoundException("ShoppingCartItem's product is null");
        }

        shoppingCartItemDTO.setProductDTO(productConverter.productToProductDTO(shoppingCartItem.getProduct()));
        shoppingCartItemDTO.setShoppingCartDTO(shoppingCartConverter
                .shoppingCartToShoppingCartDTO(shoppingCartItem.getShoppingCart()));
        shoppingCartItemDTO.setShoppingCartDTOId(shoppingCartItem.getId());
    }

    @AfterMapping
    protected void setAdditionalShoppingCartItemParameters(ShoppingCartItemDTO shoppingCartItemDTO,
                                                              @MappingTarget ShoppingCartItem shoppingCartItem) {

        if(shoppingCartItemDTO.getShoppingCartDTO() == null) {

            throw new NotFoundException("ShoppingCartItem's Shopping cart is null");
        }

        else if(shoppingCartItemDTO.getProductDTO() == null) {

            throw new NotFoundException("ShoppingCartItem's product is null");
        }

        shoppingCartItem.setProduct(productConverter.productDTOToProduct(shoppingCartItemDTO.getProductDTO()));
        shoppingCartItem.setShoppingCart(shoppingCartConverter
                .shoppingCartDTOToShoppingCart(shoppingCartItemDTO.getShoppingCartDTO()));
    }
}
