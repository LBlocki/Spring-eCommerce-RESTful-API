package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.OrderItemMapper;
import com.blocki.springrestonlinestore.api.v1.models.OrderDTO;
import com.blocki.springrestonlinestore.api.v1.models.OrderItemDTO;
import com.blocki.springrestonlinestore.core.bootstrap.TestEntityGenerator;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.OrderItemResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.OrderResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Order;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import com.blocki.springrestonlinestore.core.repositories.OrderItemRepository;
import com.blocki.springrestonlinestore.core.repositories.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderServiceImplTest {

    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    private Order order;

    private OrderItemMapper orderItemConverter = Mappers.getMapper(OrderItemMapper.class);

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Spy
    private OrderResourceAssembler orderResourceAssembler = new OrderResourceAssembler();

    @Spy
    private OrderItemResourceAssembler orderItemResourceAssembler =
            new OrderItemResourceAssembler();


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        order = testEntityGenerator.generateOrder();
    }

    @Test
    public void getOrderById() {

        //given
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));

        //when
        Resource<OrderDTO> testOderDTO = orderService.getOrderById(order.getId());

        //then
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(orderResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(OrderDTO.class));

        Mockito.verifyNoMoreInteractions(orderRepository);
        Mockito.verifyNoMoreInteractions(orderResourceAssembler);

        assertNotNull(testOderDTO);
        assertNotNull(testOderDTO.getContent().getUserDTO());
        assertNotNull(testOderDTO.getContent().getOrderItemDTOS());

        assertEquals(testOderDTO.getContent().getUserDTO().getId(), order.getUser().getId());
        assertEquals(testOderDTO.getContent().getId(), order.getId());
        assertEquals(testOderDTO.getContent().getUserDTOId(), order.getUser().getId());
        assertEquals(testOderDTO.getContent().getOrderItemDTOS().size(), order
                .getOrderItems().size());
        assertEquals(testOderDTO.getContent().getOrderStatus(), order.getOrderStatus());
        assertEquals(testOderDTO.getContent().getCreationDate(), order.getCreationDate());
    }

    @Test
    public void deleteOrderById() {

        //when
        orderService.deleteOrderById(order.getId());

        //then
        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void createPurchaseRequest() {

        //given
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));

        //when
        Resource<OrderDTO> testOrderDTO = orderService.createPurchaseRequest(order.getId());

        //then
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(orderResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(OrderDTO.class));

        Mockito.verifyNoMoreInteractions(orderRepository);
        Mockito.verifyNoMoreInteractions(orderResourceAssembler);

        assertNotNull(testOrderDTO);
        assertEquals(testOrderDTO.getContent().getOrderStatus(), Order.OrderStatus.COMPLETED);
    }

    @Test
    public void createNewOrderItem() {

        //given
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        Mockito.when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(order.getOrderItems().get(0));
        OrderItem orderItem = order.getOrderItems().get(0);

        //when
        Resource<OrderItemDTO> testCreatedOrderItemDTO = orderService
                .createNewOrderItem(order.getId(), orderItemConverter
                        .orderItemToOrderItemDTO(orderItem));

        //then
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(orderRepository, Mockito.times(1))
                .save(Mockito.any(Order.class));
        Mockito.verify(orderResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(OrderDTO.class));
        Mockito.verify(orderItemResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(OrderItemDTO.class));

        Mockito.verifyNoMoreInteractions(orderRepository);
        Mockito.verifyNoMoreInteractions(orderItemResourceAssembler);
        Mockito.verifyNoMoreInteractions(orderItemResourceAssembler);

        assertNotNull(testCreatedOrderItemDTO);
        assertNotNull(testCreatedOrderItemDTO.getContent().getProductDTO());
        assertNotNull(testCreatedOrderItemDTO.getContent().getOrderDTO());

        assertEquals(testCreatedOrderItemDTO.getContent()
                .getOrderDTOId(), orderItem.getOrder().getId());
        assertEquals(testCreatedOrderItemDTO.getContent().getId(), orderItem.getId());
        assertEquals(testCreatedOrderItemDTO.getContent()
                .getProductDTO().getId(), orderItem.getProduct().getId());
        assertEquals(testCreatedOrderItemDTO.getContent().getTotalCost(), orderItem.getTotalCost());
        assertEquals(testCreatedOrderItemDTO.getContent().getQuantity(), orderItem.getQuantity());
    }

    @Test
    public void getAllOrderItems() {

        //given
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));
        List<OrderItem> orderItemList = order.getOrderItems();


        //when
        Resources<Resource<OrderItemDTO>> testShoppingCartItemList =
                orderService.getAllOrderItems(order.getId());

        //then
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(orderRepository);

        assertNotNull(testShoppingCartItemList);

        assertEquals(testShoppingCartItemList.getContent().size(), orderItemList.size());
    }
}