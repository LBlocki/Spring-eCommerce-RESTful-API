package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Slf4j
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderItemMapper {

    private final OrderMapper orderConverter = Mappers.getMapper(OrderMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);

    abstract public OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);

    abstract public OrderItem orderItemDTOToOrderItem(OrderItemDTO orderItemDTO);

    @AfterMapping
    void setAdditionalOrderItemDTOParameters(OrderItem orderItem,
                                                    @MappingTarget OrderItemDTO orderItemDTO) {

        orderItemDTO.setProductDTO(productConverter.productToProductDTO(orderItem.getProduct()));

        if(orderItem.getOrder() != null) {

            orderItemDTO.setOrderDTO(orderConverter
                    .orderToOrderDTO(orderItem.getOrder()));
            orderItemDTO.setOrderDTOId(orderItem.getOrder().getId());
        }

        if(log.isDebugEnabled()) {

            log.debug(OrderItemMapper.class.getName()
                    + ":(orderItemToOrderItemDTO): orderItem:\n"
                    + orderItem.toString() + ",\n orderItemDTO:"
                    + orderItemDTO.toString() + "\n");
        }
    }

    @AfterMapping
    void setAdditionalOrderItemParameters(OrderItemDTO orderItemDTO,
                                                 @MappingTarget OrderItem orderItem) {


        orderItem.setProduct(productConverter.productDTOToProduct(orderItemDTO.getProductDTO()));
        orderItem.setOrder(orderConverter
                .orderDTOToOrder(orderItemDTO.getOrderDTO()));

        if(log.isDebugEnabled()) {

            log.debug(OrderItemMapper.class.getName()
                    + ":(orderItemDTOToOrderItem): orderItemDTO:\n"
                    + orderItemDTO.toString() + ",\n orderItem:"
                    + orderItem.toString() + "\n");
        }
    }
}
