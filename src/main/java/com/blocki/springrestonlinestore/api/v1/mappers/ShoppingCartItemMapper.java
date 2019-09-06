package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
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
    void setAdditionalShoppingCartItemDTOParameters(ShoppingCartItem shoppingCartItem,
                                                              @MappingTarget ShoppingCartItemDTO shoppingCartItemDTO) {

        shoppingCartItemDTO.setProductDTO(productConverter.productToProductDTO(shoppingCartItem.getProduct()));
        shoppingCartItemDTO.setShoppingCartDTO(shoppingCartConverter
                .shoppingCartToShoppingCartDTO(shoppingCartItem.getShoppingCart()));
        shoppingCartItemDTO.setShoppingCartDTOId(shoppingCartItem.getShoppingCart().getId());
    }

    @AfterMapping
    void setAdditionalShoppingCartItemParameters(ShoppingCartItemDTO shoppingCartItemDTO,
                                                              @MappingTarget ShoppingCartItem shoppingCartItem) {


        shoppingCartItem.setProduct(productConverter.productDTOToProduct(shoppingCartItemDTO.getProductDTO()));
        shoppingCartItem.setShoppingCart(shoppingCartConverter
                .shoppingCartDTOToShoppingCart(shoppingCartItemDTO.getShoppingCartDTO()));
    }
}
