package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.bootstrap.TestEntityGenerator;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderItemMapperTest {

    private final OrderItemMapper orderItemConverter = Mappers.getMapper(OrderItemMapper.class);
    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    private OrderItem orderItem;

    @Before
    public void setUp() {

       orderItem = testEntityGenerator.generateOrderItem();
    }

    @Test
    public void OrderItemToOrderItemTDO() {

        //when
        OrderItemDTO testOrderItemDTO = orderItemConverter.orderItemToOrderItemDTO(orderItem);

        //then
        assertNotNull(testOrderItemDTO);
        assertNotNull(testOrderItemDTO.getProductDTO());
        assertNotNull(testOrderItemDTO.getOrderDTO());

        assertEquals(testOrderItemDTO.getId(), orderItem.getId());
        assertEquals(testOrderItemDTO.getQuantity(), orderItem.getQuantity());
        assertEquals(testOrderItemDTO.getTotalCost(), orderItem.getTotalCost());
        assertEquals(testOrderItemDTO.getProductDTO().getId(), orderItem.getProduct().getId());
        assertEquals(testOrderItemDTO.getOrderDTO().getId(), orderItem.getOrder().getId());
        assertEquals(testOrderItemDTO.getOrderDTOId(), orderItem.getOrder().getId());
    }

    @Test
    public void OrderItemDTOToOrderItem() {

        //given
        OrderItemDTO orderItemDTO = orderItemConverter.orderItemToOrderItemDTO(orderItem);

        //when
        OrderItem testOrderItem = orderItemConverter.orderItemDTOToOrderItem(orderItemDTO);

        //then
        assertNotNull(testOrderItem);
        assertNotNull(testOrderItem.getProduct());
        assertNotNull(testOrderItem.getOrder());

        assertEquals(testOrderItem.getId(), orderItemDTO.getId());
        assertEquals(testOrderItem.getQuantity(), orderItemDTO.getQuantity());
        assertEquals(testOrderItem.getTotalCost(), orderItemDTO.getTotalCost());
        assertEquals(testOrderItem.getProduct().getId(), orderItemDTO.getId());
        assertEquals(testOrderItem.getOrder().getId(), orderItemDTO.getOrderDTO().getId());
    }
}