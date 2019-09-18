package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    private CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    @Mappings({
            @Mapping(source = "products", target = "productDTOs"),
            @Mapping(source = "order", target = "orderDTO")
    })
    abstract public UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    abstract public User userDTOToUser(UserDTO userDTO);


    @AfterMapping
    void setAdditionalUserDTOParameters(User user, @MappingTarget UserDTO userDTO) {

        if (userDTO.getOrderDTO() != null) {

            userDTO.getOrderDTO().setUserDTO(userDTO);
            userDTO.getOrderDTO().setUserDTOId(userDTO.getId());

            for(OrderItem orderItem :  user.getOrder().getOrderItems()) {

                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setOrderDTO(userDTO.getOrderDTO());
                orderItemDTO.setOrderDTOId(userDTO.getOrderDTO().getId());
                orderItemDTO.setId(orderItem.getId());
                orderItemDTO.setQuantity(orderItem.getQuantity());
                orderItemDTO.setTotalCost(orderItem.getTotalCost());

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(orderItem.getProduct().getId());
                productDTO.setCategoryDTO(categoryConverter.categoryToCategoryDTO(orderItem.getProduct().getCategory()));
                productDTO.setUserDTOId(userDTO.getId());
                productDTO.setUserDTO(userDTO);
                productDTO.setPhoto(orderItem.getProduct().getPhoto());
                productDTO.setProductStatus(orderItem.getProduct().getProductStatus());

                orderItemDTO.setProductDTO(productDTO);
                userDTO.getOrderDTO().getOrderItemDTOS().add(orderItemDTO);
            }
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
    }

    @AfterMapping
    void setAdditionalUserParameters(UserDTO userDTO, @MappingTarget User user) {


        if (user.getOrder() != null) {

            user.getOrder().setUser(user);

            for(OrderItemDTO orderItemDTO : Objects.requireNonNull(userDTO.getOrderDTO()).getOrderItemDTOS()) {

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(user.getOrder());
                orderItem.setQuantity(orderItemDTO.getQuantity());
                orderItem.setTotalCost(orderItemDTO.getTotalCost());
                orderItem.setId(orderItemDTO.getId());

                Product product = new Product();
                product.setId(orderItemDTO.getProductDTO().getId());
                product.setCategory(categoryConverter
                        .categoryDTOtoCategory(orderItemDTO.getProductDTO().getCategoryDTO()));
                product.setUser(user);
                product.setPhoto(orderItemDTO.getProductDTO().getPhoto());
                product.setProductStatus(orderItemDTO.getProductDTO().getProductStatus());

                orderItem.setProduct(product);
                user.getOrder().getOrderItems().add(orderItem);
            }
        }

        if (user.getProducts() != null && !user.getProducts().isEmpty()) {

            int i = 0;

            for( Product product : user.getProducts()) {

                product.setUser(user);
                    product.setCategory(categoryConverter.categoryDTOtoCategory(
                            Objects.requireNonNull(userDTO.getProductDTOs()).get(i).getCategoryDTO()));
            }
        }
    }
}
