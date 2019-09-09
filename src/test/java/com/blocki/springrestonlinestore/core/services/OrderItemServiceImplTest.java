package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.TestEntity;
import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.OrderItemResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import com.blocki.springrestonlinestore.core.repositories.OrderItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.hateoas.Resource;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderItemServiceImplTest {

    private final TestEntity testEntity = new TestEntity();

    private OrderItem orderItem;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Spy
    private final OrderItemResourceAssembler orderItemResourceAssembler =
            new OrderItemResourceAssembler();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        orderItem = testEntity.getOrderItem();
    }

    @Test
    public void getOrderItemById() {

        //given
        Mockito.when(orderItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(orderItem));

        //when
        Resource<OrderItemDTO> testOrderItemDTO =
                orderItemService.getOrderItemById(orderItem.getId());

        //then
        Mockito.verify(orderItemRepository, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(orderItemResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(OrderItemDTO.class));

        Mockito.verifyNoMoreInteractions(orderItemRepository);
        Mockito.verifyNoMoreInteractions(orderItemResourceAssembler);

        assertNotNull(testOrderItemDTO);
        assertNotNull(testOrderItemDTO.getContent().getOrderDTO());
        assertNotNull(testOrderItemDTO.getContent().getProductDTO());

        assertEquals(testOrderItemDTO.getContent().getId(), orderItem.getId());
        assertEquals(testOrderItemDTO.getContent().getOrderDTO().getId(),
                orderItem.getOrder().getId());
        assertEquals(testOrderItemDTO.getContent().getOrderDTOId(),
                orderItem.getOrder().getId());
        assertEquals(testOrderItemDTO.getContent().getProductDTO().getId(), orderItem.getProduct().getId());
        assertEquals(testOrderItemDTO.getContent().getQuantity(), orderItem.getQuantity());
        assertEquals(testOrderItemDTO.getContent().getTotalCost(), orderItem.getTotalCost());

    }

    @Test
    public void deleteOrderItemById() {

        //when
       orderItemService.deleteOrderItemById(orderItem.getId());

       //then
       Mockito.verify(orderItemRepository, Mockito.times(1)).deleteById(Mockito.anyLong());

       Mockito.verifyNoMoreInteractions(orderItemRepository);

    }
}