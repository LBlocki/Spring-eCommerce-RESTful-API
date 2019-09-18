package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.OrderDTO;
import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.domain.Order;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(source = "orderItems",  target = "orderItemDTOS")
    })
    abstract public OrderDTO orderToOrderDTO(Order order);

    @InheritInverseConfiguration
    abstract public Order orderDTOToOrder(OrderDTO orderDTO);

    @AfterMapping
    void setAdditionalOrderDTOParameters(Order order, @MappingTarget
            OrderDTO orderDTO) {

        if(order.getUser() != null) {

            orderDTO.setUserDTO(userConverter.userToUserDTO(order.getUser()));
            orderDTO.setUserDTOId(order.getUser().getId());
        }

        int i = 0;

        for(OrderItemDTO orderItemDTO : orderDTO.getOrderItemDTOS()) {

            orderItemDTO.setOrderDTO(orderDTO);
            orderItemDTO.setProductDTO(productConverter.productToProductDTO(
                    order.getOrderItems().get(i).getProduct()));
            orderItemDTO.setOrderDTOId(orderDTO.getId());

            i++;
        }
    }

    @AfterMapping
    void setAdditionalOrderParameters(OrderDTO orderDTO, @MappingTarget
            Order order) {

        order.setUser(userConverter.userDTOToUser(orderDTO.getUserDTO()));

        int i = 0;

        for( OrderItem orderItem : order.getOrderItems()) {

            orderItem.setOrder(order);
            orderItem.setProduct(productConverter.productDTOToProduct(
                    orderDTO.getOrderItemDTOS().get(i).getProductDTO()));

            i++;
        }
    }
}
