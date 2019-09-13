package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.mappers.OrderItemMapper;
import com.blocki.springrestonlinestore.core.bootstrap.TestEntityGenerator;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.OrderItemResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.OrderResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Order;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import com.blocki.springrestonlinestore.core.repositories.OrderItemRepository;
import com.blocki.springrestonlinestore.core.repositories.OrderRepository;
import com.blocki.springrestonlinestore.core.services.OrderService;
import com.blocki.springrestonlinestore.core.services.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class OrderControllerTest {

    private static final String ORDERS_BASIC_URL = "/api/v1/orders";

    private final TestEntityGenerator testEntity = new TestEntityGenerator();

    private Order testOrder = testEntity.generateOrder();

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private OrderItemMapper orderItemConverter = Mappers.getMapper(OrderItemMapper.class);

    private RelProvider relProvider = new RelProvider() {

        @Override
        public String getItemResourceRelFor(Class<?> aClass) {

            return "order";
        }

        @Override
        public String getCollectionResourceRelFor(Class<?> aClass) {
            return "orders";
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return true;
        }
    };

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Spy
    private OrderItemResourceAssembler orderItemResourceAssembler = new OrderItemResourceAssembler();

    @Spy
    private OrderResourceAssembler orderResourceAssembler = new OrderResourceAssembler();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        OrderService orderService = new OrderServiceImpl(orderRepository, orderResourceAssembler,
                orderItemResourceAssembler, orderItemRepository);
        OrderController orderController = new OrderController(orderService);

        TypeConstrainedMappingJackson2HttpMessageConverter messageConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        messageConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON,MediaTypes.HAL_JSON_UTF8,
                MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));

        objectMapper = messageConverter.getObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(
                new Jackson2HalModule.HalHandlerInstantiator(relProvider, null, null));

        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setMessageConverters(messageConverter).build();
    }

    @Test
    public void getOrderById() throws Exception {

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testOrder));

        mockMvc.perform(MockMvcRequestBuilders.get(ORDERS_BASIC_URL + "/" + testOrder.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(testOrder.getId().intValue())))
                .andExpect(jsonPath("$.owner_id", is(testOrder.getUser().getId().intValue())))
                .andExpect(jsonPath("$.order_items.size()",is(testOrder.getOrderItems().size())))
                .andExpect(jsonPath("$.creation_date", is(testOrder.getCreationDate().toString())))
                .andExpect(jsonPath("$.order_status", is(testOrder.getOrderStatus().toString())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/v1/orders/" +
                                testOrder.getId().intValue())))
                .andExpect(jsonPath("$._links.get_user's_order.href",
                        is("http://localhost/api/v1/users/" + testOrder.getUser().getId().intValue() + "/order")))
                .andExpect(jsonPath("$._links.create_purchase_request.href",
                        is("http://localhost/api/v1/orders/" + testOrder.getId().intValue() + "/actions/purchase")))
                .andExpect(jsonPath("$._links.delete_order.href",
                        is("http://localhost/api/v1/orders/" + testOrder.getId().intValue())))
                .andExpect(jsonPath("$._links.get_order's_user.href",
                        is("http://localhost/api/v1/users/" + testOrder.getUser().getId().intValue())))
                .andExpect(jsonPath("$._links.get_list_of_order_items.href",
                        is("http://localhost/api/v1/orders/" + testOrder.getId().intValue() + "/orderItems")));

        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void deleteOrderById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(ORDERS_BASIC_URL + "/" + testOrder.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void createPurchaseRequest() throws Exception {

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testOrder));

        mockMvc.perform(MockMvcRequestBuilders.post(ORDERS_BASIC_URL +
                "/" + testOrder.getId() + "/actions/purchase")
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.order_status", is(Order.OrderStatus.COMPLETED.toString())));

        Mockito.verify(orderRepository, Mockito.times(1)).findById(testOrder.getId());
        Mockito.verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void getAllOrderItems() throws Exception{

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testOrder));

        relProvider = new RelProvider() {

            @Override
            public String getItemResourceRelFor(Class<?> aClass) {

                return "item";
            }

            @Override
            public String getCollectionResourceRelFor(Class<?> aClass) {

                return "items";
            }

            @Override
            public boolean supports(Class<?> aClass) {
                return true;
            }
        };

        objectMapper.setHandlerInstantiator(
                new Jackson2HalModule.HalHandlerInstantiator(relProvider, null, null));

        mockMvc.perform(MockMvcRequestBuilders.get(ORDERS_BASIC_URL +
                "/" + testOrder.getId() + "/orderItems")
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._embedded.items.size()", is(testOrder.getOrderItems().size())));

        Mockito.verify(orderRepository, Mockito.times(1)).findById(testOrder.getId());
        Mockito.verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void createNewOrderItem() throws Exception{

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testOrder));
        Mockito.when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(testOrder.getOrderItems().get(0));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(testOrder);

        relProvider = new RelProvider() {

            @Override
            public String getItemResourceRelFor(Class<?> aClass) {

                return "item";
            }

            @Override
            public String getCollectionResourceRelFor(Class<?> aClass) {

                return "items";
            }

            @Override
            public boolean supports(Class<?> aClass) {
                return true;
            }
        };

        objectMapper.setHandlerInstantiator(
                new Jackson2HalModule.HalHandlerInstantiator(relProvider, null, null));

        mockMvc.perform(MockMvcRequestBuilders.post(ORDERS_BASIC_URL +
                "/" + testOrder.getId() + "/orderItems")
                .content(objectMapper.writeValueAsBytes(orderItemConverter
                        .orderItemToOrderItemDTO(testOrder.getOrderItems().get(0))))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(orderRepository, Mockito.times(1)).findById(testOrder.getId());
        Mockito.verifyNoMoreInteractions(orderRepository);
    }
}