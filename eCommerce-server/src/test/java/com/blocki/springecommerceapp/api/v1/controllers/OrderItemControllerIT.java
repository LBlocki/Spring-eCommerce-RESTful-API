package com.blocki.springecommerceapp.api.v1.controllers;

import com.blocki.springecommerceapp.api.v1.models.OrderItemDTO;
import com.blocki.springecommerceapp.core.services.OrderItemService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureRestDocs
public class OrderItemControllerIT {

    private static final String ORDER_ITEMS_BASIC_URL = "/api/v1/orderItems";




    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderItemService orderItemService;

    private OrderItemDTO testOrderItem;


    @Before
    public void setUp() {

        Assert.assertNotNull(orderItemService.getOrderItemById(1L).getContent());
        testOrderItem = orderItemService.getOrderItemById(1L).getContent();
    }

    @Test
    public void getOrderItemById() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders
                .get(ORDER_ITEMS_BASIC_URL + "/" + testOrderItem.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers
                        .is(testOrderItem.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.id", Matchers
                        .is(testOrderItem.getProductDTO().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.order_id", Matchers
                        .is(testOrderItem.getOrderDTO().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", Matchers
                        .is("http://localhost:8080/api/v1/orderItems/" + testOrderItem.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.delete_order_item.href", Matchers
                        .is("http://localhost:8080/api/v1/orderItems/" + testOrderItem.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.get_order's_items.href", Matchers
                        .is("http://localhost:8080/api/v1/orders/" + testOrderItem.getOrderDTO().getId() + "/orderItems")))
                .andDo(document("orderItems/getOrderItemById"));

    }

    @Test
    public void deleteOrderItemById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete(ORDER_ITEMS_BASIC_URL + "/" + testOrderItem.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(document("orderItems/deleteOrderItemById"));
    }
}