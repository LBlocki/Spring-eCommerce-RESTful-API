package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.OrderDTO;
import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.domain.Order;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Slf4j
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

        if(log.isDebugEnabled()) {

            log.debug(OrderMapper.class.getName() + ":(orderToOrderDTO): order:\n"
                    + order.toString() + ",\n orderDTO:" + orderDTO.toString() + "\n");
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

        if(log.isDebugEnabled()) {

            log.debug(OrderMapper.class.getName() + ":(orderDTOToOrder): orderDTO:\n"
                    + orderDTO.toString() + ",\n order:" + orderDTO.toString() + "\n");
        }
    }
}
