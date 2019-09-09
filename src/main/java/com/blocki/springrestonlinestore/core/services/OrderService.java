package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.OrderDTO;
import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

public interface OrderService {

    Resource<OrderDTO> getOrderById(Long id);

    void deleteOrderById(Long id);

    Resource<OrderDTO> createPurchaseRequest(Long id);

    Resource<OrderItemDTO> createNewOrderItem(Long id, OrderItemDTO orderItemDTO);

    Resources<Resource<OrderItemDTO>> getAllOrderItems(Long id);
}
