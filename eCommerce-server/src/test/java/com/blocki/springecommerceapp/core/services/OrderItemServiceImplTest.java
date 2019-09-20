package com.blocki.springecommerceapp.core.services;

import com.blocki.springecommerceapp.api.v1.models.OrderItemDTO;
import com.blocki.springecommerceapp.core.bootstrap.TestEntityGenerator;
import com.blocki.springecommerceapp.core.config.resourceAssemblers.OrderItemResourceAssembler;
import com.blocki.springecommerceapp.core.domain.OrderItem;
import com.blocki.springecommerceapp.core.repositories.OrderItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.hateoas.Resource;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderItemServiceImplTest {

    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

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

        orderItem = testEntityGenerator.generateOrderItem();
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