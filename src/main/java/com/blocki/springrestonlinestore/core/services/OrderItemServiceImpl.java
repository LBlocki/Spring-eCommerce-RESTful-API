package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.OrderItemMapper;
import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.OrderItemResourceAssembler;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.OrderItemRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemConverter = Mappers.getMapper(OrderItemMapper.class);
    private final OrderItemResourceAssembler orderItemResourceAssembler;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
                                OrderItemResourceAssembler orderItemResourceAssembler) {

        this.orderItemRepository = orderItemRepository;
        this.orderItemResourceAssembler = orderItemResourceAssembler;
    }

    @Override
    public Resource<OrderItemDTO> getOrderItemById(Long id) {

       return orderItemRepository
               .findById(id)
               .map(orderItemConverter::orderItemToOrderItemDTO)
               .map(orderItemResourceAssembler::toResource)
               .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteOrderItemById(Long id) {

        orderItemRepository.deleteById(id);
    }
}
