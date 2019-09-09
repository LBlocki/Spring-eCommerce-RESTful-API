package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.services.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = OrderItemController.ORDER_ITEMS_BASIC_URL, produces = "application/hal+json")
public class OrderItemController {

    static final String ORDER_ITEMS_BASIC_URL = "/api/v1/orderItems";

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {

        if(log.isDebugEnabled()) {

            log.debug(OrderItemController.class.getName()
                    + ":(constructor):Injected shopping cart item service:\n"
                    + orderItemService.toString() + "\n");
        }

        this.orderItemService = orderItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<OrderItemDTO>> getOrderItemById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(OrderItemController.class.getName()
                    + ":(getOrderItemById): ID value in path: " + id  + "\n");
        }

        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderItemById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(ProductController.class.getName() + ":(deleteOrderItemById): Id value in path: " + id + "\n");
        }

        orderItemService.deleteOrderItemById(id);

        return ResponseEntity.noContent().build();
    }
}
