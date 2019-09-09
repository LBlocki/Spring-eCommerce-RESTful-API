package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import com.blocki.springrestonlinestore.core.domain.Product;
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
            @Mapping(source = "order", target = "orderDTO")
    })
    abstract public UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    abstract public User userDTOToUser(UserDTO userDTO);


    @AfterMapping
    void setAdditionalUserDTOParameters(User user, @MappingTarget UserDTO userDTO) {

        if (userDTO.getOrderDTO() != null) {

            int i = 0;

            List<OrderItem> orderItemList = user.getOrder().getOrderItems();

            for(OrderItemDTO orderItemDTO : userDTO.getOrderDTO().getOrderItemDTOS()) {

                ProductDTO productDTO = new ProductDTO();

                productDTO.setId(orderItemList.get(i).getProduct().getId());
                productDTO.setCategoryDTO(categoryConverter
                        .categoryToCategoryDTO(orderItemList.get(i).getProduct().getCategory()));
                productDTO.setUserDTOId(userDTO.getId());
                productDTO.setUserDTO(userDTO);
                productDTO.setPhoto(orderItemList.get(i).getProduct().getPhoto());
                productDTO.setProductStatus(orderItemList.get(i).getProduct().getProductStatus());

                orderItemDTO.setProductDTO(productDTO);
                orderItemDTO.setOrderDTO(userDTO.getOrderDTO());
                orderItemDTO.setOrderDTOId(userDTO.getOrderDTO().getId());
            }

            userDTO.getOrderDTO().setUserDTO(userDTO);
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


        if (user.getOrder() != null) {

            int i = 0;

            List<OrderItemDTO> orderItemDTOList = Objects.requireNonNull(userDTO.getOrderDTO()).getOrderItemDTOS();

            for(OrderItem orderItem : user.getOrder().getOrderItems()) {

                Product product = new Product();

                product.setId(orderItemDTOList.get(i).getProductDTO().getId());
                product.setCategory(categoryConverter
                        .categoryDTOtoCategory(orderItemDTOList.get(i).getProductDTO().getCategoryDTO()));
                product.setUser(user);
                product.setPhoto(orderItemDTOList.get(i).getProductDTO().getPhoto());
                product.setProductStatus(orderItemDTOList.get(i).getProductDTO().getProductStatus());

                orderItem.setProduct(product);
                orderItem.setOrder(user.getOrder());
            }

            user.getOrder().setUser(user);
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
