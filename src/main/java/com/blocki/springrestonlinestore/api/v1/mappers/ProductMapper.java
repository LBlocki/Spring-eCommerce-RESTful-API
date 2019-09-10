package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Slf4j
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);

    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    abstract public ProductDTO productToProductDTO(Product product);

    @InheritInverseConfiguration
    abstract public Product productDTOToProduct(ProductDTO productDTO);

    @AfterMapping
    void setAdditionalProductDTOParameters(Product product, @MappingTarget ProductDTO productDTO) {


        productDTO.setCategoryDTO(categoryConverter.categoryToCategoryDTO(product.getCategory()));

        if(product.getUser() != null) {

            productDTO.setUserDTOId(product.getUser().getId());
            productDTO.setUserDTO(userConverter.userToUserDTO(product.getUser()));
        }


        if(log.isDebugEnabled()) {

            log.debug(ProductMapper.class.getName() + ":(productToProductDTO): product:\n"
                    + product.toString() + ",\n productDTO:" + productDTO.toString() + "\n");
        }
    }

    @AfterMapping
    void setAdditionalProductParameters(ProductDTO productDTO, @MappingTarget Product product) {
        

        product.setCategory(categoryConverter.categoryDTOtoCategory(productDTO.getCategoryDTO()));

        if(productDTO.getUserDTO() != null) {

            product.setUser(userConverter.userDTOToUser(productDTO.getUserDTO()));
        }

            if(log.isDebugEnabled()) {

            log.debug(ProductMapper.class.getName() + ":(productDTOToProduct): productDTO:\n"
                    + productDTO.toString() + ",\n product:" + product.toString() + "\n");
        }
    }
}
