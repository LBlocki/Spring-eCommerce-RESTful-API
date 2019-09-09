package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import org.springframework.hateoas.Resource;

public interface OrderItemService {

    Resource<OrderItemDTO> getOrderItemById(Long id);

    void deleteOrderItemById(Long id);
}
