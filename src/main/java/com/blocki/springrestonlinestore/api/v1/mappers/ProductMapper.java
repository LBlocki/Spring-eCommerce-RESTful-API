package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);

    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    abstract public ProductDTO productToProductDTO(Product product);

    @InheritInverseConfiguration
    abstract public Product productDTOToProduct(ProductDTO productDTO);

    @AfterMapping
    void setAdditionalProductDTOParameters(Product product, @MappingTarget ProductDTO productDTO) {

        productDTO.setUserDTO(userConverter.userToUserDTO(product.getUser()));
        productDTO.setCategoryDTO(categoryConverter.categoryToCategoryDTO(product.getCategory()));
        productDTO.setUserDTOId(product.getUser().getId());
    }

    @AfterMapping
    void setAdditionalProductParameters(ProductDTO productDTO, @MappingTarget Product product) {

        product.setUser(userConverter.userDTOToUser(productDTO.getUserDTO()));
        product.setCategory(categoryConverter.categoryDTOtoCategory(productDTO.getCategoryDTO()));
    }
}
