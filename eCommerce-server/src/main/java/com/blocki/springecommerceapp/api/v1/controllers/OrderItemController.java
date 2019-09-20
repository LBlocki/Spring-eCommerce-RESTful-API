package com.blocki.springecommerceapp.api.v1.controllers;

import com.blocki.springecommerceapp.api.v1.models.OrderItemDTO;
import com.blocki.springecommerceapp.core.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = OrderItemController.ORDER_ITEMS_BASIC_URL, produces = "application/hal+json")
public class OrderItemController {

    static final String ORDER_ITEMS_BASIC_URL = "/api/v1/orderItems";

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {

        this.orderItemService = orderItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<OrderItemDTO>> getOrderItemById(@PathVariable final Long id) {

        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderItemById(@PathVariable final Long id) {

        orderItemService.deleteOrderItemById(id);

        return ResponseEntity.noContent().build();
    }
}
