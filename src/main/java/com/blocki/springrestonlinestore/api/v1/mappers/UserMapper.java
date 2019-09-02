package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    private CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    @Mappings({
            @Mapping(source = "products", target = "productDTOs"),
            @Mapping(source = "shoppingCart", target = "shoppingCartDTO")
    })
    abstract public UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    abstract public User userDTOToUser(UserDTO userDTO);


    @AfterMapping
    protected void setAdditionalUserDTOParameters(User user, @MappingTarget UserDTO userDTO) {

        if (userDTO.getShoppingCartDTO() != null) {
            userDTO.getShoppingCartDTO().setUserDTO(userDTO);
        }

        if (userDTO.getProductDTOs() != null && !userDTO.getProductDTOs().isEmpty()) {

            int i = 0;

            for( ProductDTO product : userDTO.getProductDTOs()) {

                product.setUserDTO(userDTO);
                product.setCategoryDTO(categoryConverter.categoryToCategoryDTO(user.getProducts().get(i).getCategory()));
                product.setUserDTOId(user.getId());
            }

        }
    }

    @AfterMapping
    protected void setAdditionalUserParameters(UserDTO userDTO, @MappingTarget User user) {


        if (user.getShoppingCart() != null) {

            user.getShoppingCart().setUser(user);
        }

        if (user.getProducts() != null && !user.getProducts().isEmpty()) {

            int i = 0;

            for( Product product : user.getProducts()) {

                product.setUser(user);

                product.setCategory(categoryConverter.categoryDTOtoCategory(userDTO.getProductDTOs().get(i).getCategoryDTO()));
            }
        }
    }
}
