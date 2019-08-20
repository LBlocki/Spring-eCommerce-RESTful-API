package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.ProductDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "category", target = "categoryDTO")
    @Mapping(source = "shoppingCartItems", target = "shoppingCartItemDTOs")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "userDTO", target = "user")
    @Mapping(source = "categoryDTO", target = "category")
    @Mapping(source = "shoppingCartItemDTOs", target = "shoppingCartItems")
    Product productDTOToProduct(ProductDTO productDTO);
}
