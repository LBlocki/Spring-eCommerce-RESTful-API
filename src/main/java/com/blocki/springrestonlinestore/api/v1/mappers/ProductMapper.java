package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

    private UserMapper userConverter = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "user", target = "userDTO"),
            @Mapping(source = "category", target = "categoryDTO")
    })
    abstract public ProductDTO productToProductDTO(Product product);

    @InheritInverseConfiguration
    abstract public Product productDTOToProduct(ProductDTO productDTO);

    @AfterMapping
    protected void setAdditionalProductDTOParameters(Product product, @MappingTarget ProductDTO productDTO) {

        if(product.getUser() == null) {

            throw new NotFoundException("Product's user is null");
        }

        productDTO.setUserDTOId(product.getUser().getId());
    }

    @AfterMapping
    protected void setAdditionalProductParameters(ProductDTO productDTO, @MappingTarget Product product) {

        if(productDTO.getUserDTO() == null) {

            throw new NotFoundException("ProductDTO's userDTO is null");
        }

        product.setUser(userConverter.userDTOToUser(productDTO.getUserDTO()));
    }
}
