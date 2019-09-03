package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    abstract public ProductDTO productToProductDTO(Product product);

    abstract public Product productDTOToProduct(ProductDTO productDTO);

    @AfterMapping
    protected void setAdditionalProductDTOParameters(Product product, @MappingTarget ProductDTO productDTO) {

        if(product.getUser() == null) {

            throw new NotFoundException("Product's user is null");
        }

        else if(product.getCategory() == null) {

            throw new NotFoundException("Product's category is null");
        }

        productDTO.setUserDTO(userConverter.userToUserDTO(product.getUser()));
        productDTO.setUserDTOId(product.getUser().getId());
        productDTO.setCategoryDTO(categoryConverter.categoryToCategoryDTO(product.getCategory()));
    }

    @AfterMapping
    protected void setAdditionalProductParameters(ProductDTO productDTO, @MappingTarget Product product) {

        if(productDTO.getUserDTO() == null) {

            throw new NotFoundException("Product's user is null");
        }

        else if(productDTO.getCategoryDTO() == null) {

            throw new NotFoundException("Product's category is null");
        }

        product.setUser(userConverter.userDTOToUser(productDTO.getUserDTO()));
        product.setCategory(categoryConverter.categoryDTOtoCategory(productDTO.getCategoryDTO()));
    }
}
