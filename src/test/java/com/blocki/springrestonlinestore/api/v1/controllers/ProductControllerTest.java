package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.services.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

    private static final String PRODUCTS_BASIC_URL = "/api/v1/products";

    @Autowired
    private ProductServiceImpl productService;

    private ProductDTO testProduct;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {

        testProduct = productService.getProductById(1L).getContent();
    }

    @Test
    public void getProductById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCTS_BASIC_URL + "/" + testProduct.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(testProduct.getId().intValue())))
                .andExpect(jsonPath("$.name", is(testProduct.getName())))
                .andExpect(jsonPath("$.description", is(testProduct.getDescription())))
                .andExpect(jsonPath("$.cost", is(testProduct.getCost().doubleValue())))
                .andExpect(jsonPath("$.photo", hasSize(testProduct.getPhoto().length)))
                .andExpect(jsonPath("$.category.id", is(testProduct.getCategoryDTO().getId().intValue())))
                .andExpect(jsonPath("$.product_status", is(testProduct.getProductStatus().toString())))
                .andExpect(jsonPath("$.creation_date", is(testProduct.getCreationDate().toString())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/v1/products/1")));

    }

    @Test
    public void getProductByName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCTS_BASIC_URL + "/name/" + testProduct.getName())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(testProduct.getId().intValue())))
                .andExpect(jsonPath("$.name", is(testProduct.getName())))
                .andExpect(jsonPath("$.description", is(testProduct.getDescription())))
                .andExpect(jsonPath("$.cost", is(testProduct.getCost().doubleValue())))
                .andExpect(jsonPath("$.photo", hasSize(testProduct.getPhoto().length)))
                .andExpect(jsonPath("$.category.id", is(testProduct.getCategoryDTO().getId().intValue())))
                .andExpect(jsonPath("$.product_status", is(testProduct.getProductStatus().toString())))
                .andExpect(jsonPath("$.creation_date", is(testProduct.getCreationDate().toString())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/v1/products/1")));
    }

    @Test
    public void patchProduct() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch(PRODUCTS_BASIC_URL +
                "/" + testProduct.getId())
                .accept(MediaTypes.HAL_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testProduct))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteProductById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(PRODUCTS_BASIC_URL + "/" + testProduct.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}