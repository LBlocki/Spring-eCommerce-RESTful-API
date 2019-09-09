package com.blocki.springrestonlinestore.core.config.resourceAssemblers;



import com.blocki.springrestonlinestore.api.v1.controllers.OrderController;
import com.blocki.springrestonlinestore.api.v1.controllers.UserController;
import com.blocki.springrestonlinestore.api.v1.models.OrderDTO;
import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrderResourceAssembler implements ResourceAssembler<OrderDTO, Resource<OrderDTO>> {

    @Override
    public Resource<OrderDTO> toResource(OrderDTO orderDTO) {

        return new Resource<>(orderDTO,
                linkTo(methodOn(OrderController.class).getOrderById(orderDTO.getId()))
                        .withSelfRel().withType("GET"),
                linkTo(methodOn(UserController.class).getOrder(orderDTO.getUserDTO().getId()))
                        .withRel("get user's order").withType("GET"),
                linkTo(methodOn(OrderController.class).
                        createNewOrderItem(orderDTO.getId(), new OrderItemDTO()))
                        .withRel("create order item").withType("POST"),
                linkTo(methodOn(OrderController.class).createPurchaseRequest(orderDTO.getId()))
                        .withRel("create purchase request").withType("POST"),
                linkTo(methodOn(OrderController.class).getOrderById(orderDTO.getId()))
                        .withRel("delete order").withType("DELETE"),
                linkTo(methodOn(UserController.class).getUserById(orderDTO.getUserDTOId()))
                        .withRel("get order's user").withType("GET"),
                linkTo(methodOn(OrderController.class).getAllOrderItems(orderDTO.getId()))
                        .withRel("get list of order items").withType("GET"));
    }
}
