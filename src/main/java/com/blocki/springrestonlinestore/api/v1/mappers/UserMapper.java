package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import com.blocki.springrestonlinestore.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Objects;

@Slf4j
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
    void setAdditionalUserDTOParameters(User user, @MappingTarget UserDTO userDTO) {

        if (userDTO.getShoppingCartDTO() != null) {

            int i = 0;

            List<ShoppingCartItem> shoppingCartItemList = user.getShoppingCart().getShoppingCartItems();

            for(ShoppingCartItemDTO shoppingCartItem : userDTO.getShoppingCartDTO().getShoppingCartItemDTOs()) {

                ProductDTO productDTO = new ProductDTO();

                productDTO.setId(shoppingCartItemList.get(i).getProduct().getId());
                productDTO.setCategoryDTO(categoryConverter
                        .categoryToCategoryDTO(shoppingCartItemList.get(i).getProduct().getCategory()));
                productDTO.setUserDTOId(userDTO.getId());
                productDTO.setUserDTO(userDTO);
                productDTO.setPhoto(shoppingCartItemList.get(i).getProduct().getPhoto());
                productDTO.setProductStatus(shoppingCartItemList.get(i).getProduct().getProductStatus());

                shoppingCartItem.setProductDTO(productDTO);
                shoppingCartItem.setShoppingCartDTO(userDTO.getShoppingCartDTO());
                shoppingCartItem.setShoppingCartDTOId(userDTO.getShoppingCartDTO().getId());
            }

            userDTO.getShoppingCartDTO().setUserDTO(userDTO);
        }

        if (userDTO.getProductDTOs() != null && !userDTO.getProductDTOs().isEmpty()) {

            int i = 0;

            for( ProductDTO product : userDTO.getProductDTOs()) {

                product.setUserDTO(userDTO);
                product.setCategoryDTO(categoryConverter.categoryToCategoryDTO(
                        user.getProducts().get(i).getCategory()));
                product.setUserDTOId(user.getId());
            }
        }

        if(log.isDebugEnabled()) {

            log.debug(UserMapper.class.getName() + ":(userToUserDTO): user:\n"
                    + user.toString() + ",\n userDTO:" + userDTO.toString() + "\n");
        }

    }

    @AfterMapping
    void setAdditionalUserParameters(UserDTO userDTO, @MappingTarget User user) {


        if (user.getShoppingCart() != null) {

            int i = 0;

            List<ShoppingCartItemDTO> shoppingCartItemDTOList = Objects.requireNonNull(userDTO.getShoppingCartDTO()).getShoppingCartItemDTOs();

            for(ShoppingCartItem shoppingCartItem : user.getShoppingCart().getShoppingCartItems()) {

                Product product = new Product();

                product.setId(shoppingCartItemDTOList.get(i).getProductDTO().getId());
                product.setCategory(categoryConverter
                        .categoryDTOtoCategory(shoppingCartItemDTOList.get(i).getProductDTO().getCategoryDTO()));
                product.setUser(user);
                product.setPhoto(shoppingCartItemDTOList.get(i).getProductDTO().getPhoto());
                product.setProductStatus(shoppingCartItemDTOList.get(i).getProductDTO().getProductStatus());

                shoppingCartItem.setProduct(product);
                shoppingCartItem.setShoppingCart(user.getShoppingCart());
            }

            user.getShoppingCart().setUser(user);
        }

        if (user.getProducts() != null && !user.getProducts().isEmpty()) {

            int i = 0;

            for( Product product : user.getProducts()) {

                product.setUser(user);
                    product.setCategory(categoryConverter.categoryDTOtoCategory(
                            Objects.requireNonNull(userDTO.getProductDTOs()).get(i).getCategoryDTO()));
            }
        }

        if(log.isDebugEnabled()) {

            log.debug(UserMapper.class.getName() + ":(userDTOToUser): userDTO:\n"
                    + userDTO.toString() + ",\n user:" + user.toString() + "\n");
        }
    }
}
