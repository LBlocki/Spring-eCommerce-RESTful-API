package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.OrderDTO;
import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@ExposesResourceFor(value = OrderDTO.class)
@RequestMapping(value = OrderController.ORDERS_BASIC_URL, produces = "application/hal+json")
public class OrderController {

    static final String ORDERS_BASIC_URL = "/api/v1/orders";

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {

        if(log.isDebugEnabled()) {

            log.debug(OrderController.class.getName() + ":(constructor):Injected order service:\n"
                    + orderService.toString() + "\n");
        }

        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<OrderDTO>> getOrderById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(OrderController.class.getName()
                    + ":(getOrderById): ID value in path: " + id  + "\n");
        }

        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(OrderController.class.getName()
                    + ":(deleteOrderById): Id value in path: " + id + "\n");
        }

        orderService.deleteOrderById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/actions/purchase")
    public ResponseEntity<Resource<OrderDTO>> createPurchaseRequest(@PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(OrderController.class.getName()
                    + ":(createPurchaseRequest): Id value in path: " + id + "\n");
        }

        final Resource<OrderDTO> orderDTOResource = orderService.createPurchaseRequest(id);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}/actions/purchase")
                        .buildAndExpand(orderDTOResource.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(orderDTOResource);
    }

    @GetMapping("/{id}/orderItems")
    public ResponseEntity<Resources<Resource<OrderItemDTO>>> getAllOrderItems(
            @PathVariable final Long id) {

        if(log.isDebugEnabled()) {

            log.debug(OrderController.class.getName()
                    + ":(getAllOrderItems): Id value in path: " + id + "\n");
        }

       return ResponseEntity.ok(orderService.getAllOrderItems(id));
    }

    @PostMapping("/{id}/orderItems")
    public ResponseEntity<Resource<OrderItemDTO>> createNewOrderItem(
            @PathVariable final Long id, @RequestBody @Valid final OrderItemDTO orderItemDTO) {

        if(log.isDebugEnabled()) {

            log.debug(OrderController.class.getName() +
                    ":(createNewOrderItem): Id value in path: " + id + "," +
                    " order item passed in path:" + orderItemDTO.toString() + "\n");
        }

        final Resource<OrderItemDTO> orderItemDTOResource =
                orderService.createNewOrderItem(id, orderItemDTO);

        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}/orderItems")
                .buildAndExpand(orderItemDTOResource.getId())
                .toUri();

        return ResponseEntity.created(uri).body(orderItemDTOResource);
    }
}
