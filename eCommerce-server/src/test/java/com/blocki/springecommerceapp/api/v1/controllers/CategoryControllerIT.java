package com.blocki.springecommerceapp.api.v1.controllers;

import com.blocki.springecommerceapp.api.v1.models.CategoryDTO;
import com.blocki.springecommerceapp.core.services.CategoryService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class CategoryControllerIT {

    private static final String CATEGORIES_BASIC_URL = "/api/v1/categories";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    private CategoryDTO testCategory;

    @Before
    public void setUp() {

        Assert.assertNotNull(categoryService.getCategoryById(1L).getContent());
        testCategory =  categoryService.getCategoryById(1L).getContent();
    }

    @Test
    public void getAllCategories() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORIES_BASIC_URL).accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._embedded.categories[0].id",
                        is(testCategory.getId().intValue())))
                .andExpect(jsonPath("$._embedded.categories[0].name",
                        is(testCategory.getName())))
                .andExpect(jsonPath("$._embedded.categories[0]._links.self.href",
                        is("http://localhost:8080/api/v1/categories/" + testCategory.getId().intValue())))
                .andExpect(jsonPath("$._embedded.categories[0]._links.get_list_of_categories.href",
                        is("http://localhost:8080/api/v1/categories")))
                .andDo(document("categories/getAllCategories"));

    }

    @Test
    public void getCategoryById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORIES_BASIC_URL + "/" + testCategory.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id",
                        is(testCategory.getId().intValue())))
                .andExpect(jsonPath("$.name",
                        is(testCategory.getName())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost:8080/api/v1/categories/" + testCategory.getId().intValue())))
                .andExpect(jsonPath("$._links.get_list_of_categories.href",
                        is("http://localhost:8080/api/v1/categories")))
                .andDo(document("categories/getCategoryById"));

    }
}