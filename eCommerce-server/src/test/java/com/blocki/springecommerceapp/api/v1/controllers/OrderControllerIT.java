package com.blocki.springecommerceapp.api.v1.controllers;

import com.blocki.springecommerceapp.api.v1.models.OrderDTO;
import com.blocki.springecommerceapp.core.domain.Order;
import com.blocki.springecommerceapp.core.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureRestDocs
public class OrderControllerIT {

    private static final String ORDERS_BASIC_URL = "/api/v1/orders";

    @Autowired
    private OrderService orderService;

    private OrderDTO testOrder;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {

        testOrder = orderService.getOrderById(1L).getContent();

    }

    @Test
    public void getOrderById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(ORDERS_BASIC_URL + "/" + testOrder.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(testOrder.getId().intValue())))
                .andExpect(jsonPath("$.owner_id", is(testOrder.getUserDTO().getId().intValue())))
                .andExpect(jsonPath("$.order_items.size()",is(testOrder.getOrderItemDTOS().size())))
                .andExpect(jsonPath("$.creation_date", is(testOrder.getCreationDate().toString())))
                .andExpect(jsonPath("$.order_status", is(testOrder.getOrderStatus().toString())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost:8080/api/v1/orders/" +
                                testOrder.getId().intValue())))
                .andExpect(jsonPath("$._links.get_user's_order.href",
                        is("http://localhost:8080/api/v1/users/" + testOrder.getUserDTO().getId().intValue() + "/order")))
                .andExpect(jsonPath("$._links.create_purchase_request.href",
                        is("http://localhost:8080/api/v1/orders/" + testOrder.getId().intValue() + "/actions/purchase")))
                .andExpect(jsonPath("$._links.delete_order.href",
                        is("http://localhost:8080/api/v1/orders/" + testOrder.getId().intValue())))
                .andExpect(jsonPath("$._links.get_order's_user.href",
                        is("http://localhost:8080/api/v1/users/" + testOrder.getUserDTO().getId().intValue())))
                .andExpect(jsonPath("$._links.get_list_of_order_items.href",
                        is("http://localhost:8080/api/v1/orders/" + testOrder.getId().intValue() + "/orderItems")))
                .andDo(document("orders/getOrderById"));

    }

    @Test
    public void deleteOrderById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(ORDERS_BASIC_URL + "/" + testOrder.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(document("orders/deleteOrderById"));

    }

    @Test
    public void createPurchaseRequest() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post(ORDERS_BASIC_URL +
                "/" + testOrder.getId() + "/actions/purchase")
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.order_status", is(Order.OrderStatus.COMPLETED.toString())))
                .andDo(document("orders/createPurchaseRequest"));
    }

    @Test
    public void getAllOrderItems() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get(ORDERS_BASIC_URL +
                "/" + testOrder.getId() + "/orderItems")
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$._embedded.items.size()", is(testOrder.getOrderItemDTOS().size())))
                .andDo(document("orders/getAllOrderItems"));

    }

    @Test
    public void createNewOrderItem() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post(ORDERS_BASIC_URL +
                "/" + testOrder.getId() + "/orderItems")
                .accept(MediaTypes.HAL_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testOrder.getOrderItemDTOS().get(0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document("orders/createNewOrderItem"));
    }
}