package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
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
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "userDTO", target = "user")
    @Mapping(source = "categoryDTO", target = "category")
    Product productDTOToProduct(ProductDTO productDTO);
}
