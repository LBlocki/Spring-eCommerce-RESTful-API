package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.mappers.OrderItemMapper;
import com.blocki.springrestonlinestore.core.bootstrap.TestEntityGenerator;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.OrderItemResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.OrderItem;
import com.blocki.springrestonlinestore.core.repositories.OrderItemRepository;
import com.blocki.springrestonlinestore.core.services.OrderItemService;
import com.blocki.springrestonlinestore.core.services.OrderItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

public class OrderItemControllerTest {

    private static final String ORDER_ITEMS_BASIC_URL = "/api/v1/orderItems";

    private final TestEntityGenerator testEntity = new TestEntityGenerator();

    private OrderItem testOrderItem = testEntity.generateOrderItem();

    private final OrderItemMapper orderItemConverter = Mappers.getMapper(OrderItemMapper.class);

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private RelProvider relProvider = new RelProvider() {

        @Override
        public String getItemResourceRelFor(Class<?> aClass) {

            return "orderItem";
        }

        @Override
        public String getCollectionResourceRelFor(Class<?> aClass) {
            return "orderItems";
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return true;
        }
    };

    @Mock
    private OrderItemRepository orderItemRepository;

    @Spy
    private OrderItemResourceAssembler orderItemResourceAssembler = new OrderItemResourceAssembler();


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        OrderItemService orderService = new OrderItemServiceImpl(orderItemRepository, orderItemResourceAssembler);
        OrderItemController orderItemController = new OrderItemController(orderService);

        TypeConstrainedMappingJackson2HttpMessageConverter messageConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        messageConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON,MediaTypes.HAL_JSON_UTF8,
                MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));

        objectMapper = messageConverter.getObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(
                new Jackson2HalModule.HalHandlerInstantiator(relProvider, null, null));

        mockMvc = MockMvcBuilders.standaloneSetup(orderItemController)
                .setMessageConverters(messageConverter).build();
    }

    @Test
    public void getOrderItemById() throws Exception {

        Mockito.when(orderItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testOrderItem));

        mockMvc.perform(MockMvcRequestBuilders
                .get(ORDER_ITEMS_BASIC_URL + "/" + testOrderItem.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers
                        .is(testOrderItem.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.id", Matchers
                        .is(testOrderItem.getProduct().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.order_id", Matchers
                        .is(testOrderItem.getOrder().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity", Matchers
                        .is(testOrderItem.getQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total_cost", Matchers
                        .is(testOrderItem.getTotalCost().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", Matchers
                        .is("http://localhost/api/v1/orderItems/" + testOrderItem.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.delete_order_item.href", Matchers
                        .is("http://localhost/api/v1/orderItems/" + testOrderItem.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.get_order's_items.href", Matchers
                        .is("http://localhost/api/v1/orders/" + testOrderItem.getOrder().getId() + "/orderItems")));

        Mockito.verify(orderItemRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(orderItemRepository);
    }

    @Test
    public void deleteOrderItemById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete(ORDER_ITEMS_BASIC_URL + "/" + testOrderItem.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}