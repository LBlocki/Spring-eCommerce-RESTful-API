package com.blocki.springecommerceapp.core.services;

import com.blocki.springecommerceapp.api.v1.controllers.OrderController;
import com.blocki.springecommerceapp.api.v1.mappers.OrderItemMapper;
import com.blocki.springecommerceapp.api.v1.mappers.OrderMapper;
import com.blocki.springecommerceapp.api.v1.models.OrderDTO;
import com.blocki.springecommerceapp.api.v1.models.OrderItemDTO;
import com.blocki.springecommerceapp.core.config.resourceAssemblers.OrderItemResourceAssembler;
import com.blocki.springecommerceapp.core.config.resourceAssemblers.OrderResourceAssembler;
import com.blocki.springecommerceapp.core.domain.Order;
import com.blocki.springecommerceapp.core.domain.User;
import com.blocki.springecommerceapp.core.exceptions.NotFoundException;
import com.blocki.springecommerceapp.core.repositories.OrderItemRepository;
import com.blocki.springecommerceapp.core.repositories.OrderRepository;
import com.blocki.springecommerceapp.core.repositories.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderConverter = Mappers.getMapper(OrderMapper.class);
    private final OrderItemMapper orderItemConverter = Mappers.getMapper(OrderItemMapper.class);

    private final OrderRepository orderRepository;
    private final OrderResourceAssembler orderResourceAssembler;
    private final OrderItemResourceAssembler orderItemResourceAssembler;

    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderResourceAssembler orderResourceAssembler,
                            OrderItemResourceAssembler orderItemResourceAssembler,
                            OrderItemRepository orderItemRepository, UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.orderResourceAssembler = orderResourceAssembler;
        this.orderItemResourceAssembler = orderItemResourceAssembler;
        this.orderItemRepository = orderItemRepository;


        this.userRepository = userRepository;
    }

    @Override
    public Resource<OrderDTO> getOrderById(Long id) {

        return orderRepository
                .findById(id)
                .map(orderConverter::orderToOrderDTO)
                .map(orderResourceAssembler::toResource)
                .orElseThrow(NotFoundException::new);
    }


    private void saveOrder(OrderDTO orderDTO) {

        Order order = orderRepository.save(orderConverter
                .orderDTOToOrder(orderDTO));

        orderResourceAssembler.toResource(orderConverter
                .orderToOrderDTO(order));
    }

    @Override
    public void deleteOrderById(Long id) {

        Optional<Order> order = orderRepository.findById(id);

        if(order.isPresent()) {

           User  optionalUser = userRepository.findById(order.get().getUser().getId()).orElseThrow(NotFoundException::new);

           optionalUser.setOrder(null);
           userRepository.save(optionalUser);
        }

        orderRepository.deleteById(id);
    }

    @Override
    public Resource<OrderDTO> createPurchaseRequest(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        OrderDTO orderDTO = orderConverter.orderToOrderDTO(order);

        orderDTO.setOrderStatus(Order.OrderStatus.COMPLETED);

        //here it would go to the orders history

        return orderResourceAssembler.toResource(orderDTO);
    }

    @Override
    public Resource<OrderItemDTO> createNewOrderItem(Long id, OrderItemDTO orderItemDTO) {

        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        OrderDTO orderDTO = orderConverter.orderToOrderDTO(order);

        orderItemDTO.setOrderDTO(orderDTO);
        orderItemDTO.setOrderDTOId(orderDTO.getId());

        orderItemDTO = orderItemConverter.orderItemToOrderItemDTO(orderItemRepository
                .save(orderItemConverter.orderItemDTOToOrderItem(orderItemDTO)));

        orderDTO.getOrderItemDTOS().add(orderItemDTO);

        saveOrder(orderDTO);

        return orderItemResourceAssembler.toResource(orderItemDTO);
    }

    @Override
    public Resources<Resource<OrderItemDTO>> getAllOrderItems(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        OrderDTO orderDTO = orderConverter.orderToOrderDTO(order);

        List<Resource<OrderItemDTO>> shoppingCartItemList = orderDTO
                .getOrderItemDTOS()
                .stream()
                .map(orderItemResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(shoppingCartItemList,
                linkTo(methodOn(OrderController.class)
                        .getOrderById(id)).withRel("get order item's order").withType("GET"),
                linkTo(methodOn(OrderController.class)
                        .getAllOrderItems(id)).withSelfRel().withType("GET"),
                linkTo(methodOn(OrderController.class).createNewOrderItem(orderDTO.getId(), new OrderItemDTO()))
                        .withRel("create_order_item").withType("POST"));
    }
}
