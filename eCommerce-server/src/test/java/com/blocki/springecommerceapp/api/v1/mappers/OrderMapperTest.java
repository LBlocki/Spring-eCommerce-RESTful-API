package com.blocki.springecommerceapp.api.v1.mappers;

import com.blocki.springecommerceapp.api.v1.models.OrderDTO;
import com.blocki.springecommerceapp.core.bootstrap.TestEntityGenerator;
import com.blocki.springecommerceapp.core.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderMapperTest {

    private final OrderMapper orderConverter = Mappers.getMapper(OrderMapper.class);
    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    private Order order;

    @Before
    public void setUp() {

        order = testEntityGenerator.generateOrder();
    }

    @Test
    public void OrderToOrderDTO() {

        //when
        OrderDTO testOrderDTO = orderConverter.orderToOrderDTO(order);

        //then
        assertNotNull(testOrderDTO);
        assertNotNull(testOrderDTO.getUserDTO());
        assertNotNull(testOrderDTO.getOrderItemDTOS());

        assertEquals(testOrderDTO.getOrderStatus(), order.getOrderStatus());
        assertEquals(testOrderDTO.getCreationDate(), order.getCreationDate());
        assertEquals(testOrderDTO.getId(), order.getId());
        assertEquals(testOrderDTO.getUserDTOId(), order.getUser().getId());
        assertEquals(testOrderDTO.getUserDTO().getId(), order.getUser().getId());
        assertEquals(testOrderDTO.getOrderItemDTOS().size(), order.getOrderItems().size());
    }

    @Test
    public void OrderDTOToOrder() {

        //given
        OrderDTO orderDTO = orderConverter.orderToOrderDTO(order);

        //when
        Order testOrder = orderConverter.orderDTOToOrder(orderDTO);

        //then
        assertNotNull(testOrder);
        assertNotNull(testOrder.getUser());
        assertNotNull(testOrder.getOrderItems());

        assertEquals(testOrder.getOrderStatus(), orderDTO.getOrderStatus());
        assertEquals(testOrder.getCreationDate(), orderDTO.getCreationDate());
        assertEquals(testOrder.getId(), orderDTO.getId());
        assertEquals(testOrder.getUser().getId(), orderDTO.getUserDTO().getId());
        assertEquals(testOrder.getOrderItems().size(), orderDTO.getOrderItemDTOS().size());
    }
}