package com.blocki.springecommerceapp.api.v1.controllers;

import com.blocki.springecommerceapp.api.v1.models.UserDTO;
import com.blocki.springecommerceapp.core.services.OrderServiceImpl;
import com.blocki.springecommerceapp.core.services.ProductServiceImpl;
import com.blocki.springecommerceapp.core.services.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureRestDocs
public class UserControllerIT {

    private static final String USERS_BASIC_URL = "/api/v1/users";

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private OrderServiceImpl orderService;

    private UserDTO testUser;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {

        Assert.assertNotNull(userService.getUserById(1L));

        testUser = userService.getUserById(1L).getContent();

    }

    @Test
    public void getAllUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(USERS_BASIC_URL)
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._embedded.users[0].id", is(testUser.getId().intValue())))
                .andDo(document("users/getAllUsers"));
    }

    @Test
    public void getUserById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(USERS_BASIC_URL + "/" + testUser.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(testUser.getId().intValue())))
                .andExpect(jsonPath("$.address", is(testUser.getAddress())))
                .andExpect(jsonPath("$.country", is(testUser.getCountry())))
                .andExpect(jsonPath("$.emailAddress", is(testUser.getEmailAddress())))
                .andExpect(jsonPath("$.username", is(testUser.getUsername())))
                .andExpect(jsonPath("$.gender", is(testUser.getGender().toString())))
                .andExpect(jsonPath("$.products.size()", is(testUser.getProductDTOs().size())))
                .andExpect(jsonPath("$.first_name", is(testUser.getFirstName())))
                .andExpect(jsonPath("$.last_name", is(testUser.getLastName())))
                .andExpect(jsonPath("$.phone_number", is(testUser.getPhoneNumber())))
                .andExpect(jsonPath("$.creation_date", is(testUser.getCreationDate().toString())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost:8080/api/v1/users/1")))
                .andDo(document("users/getUserById"));
    }

    @Test
    public void getUserByUsername() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(USERS_BASIC_URL + "/name/" + testUser.getUsername())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(testUser.getId().intValue())))
                .andExpect(jsonPath("$.address", is(testUser.getAddress())))
                .andExpect(jsonPath("$.country", is(testUser.getCountry())))
                .andExpect(jsonPath("$.emailAddress", is(testUser.getEmailAddress())))
                .andExpect(jsonPath("$.username", is(testUser.getUsername())))
                .andExpect(jsonPath("$.gender", is(testUser.getGender().toString())))
                .andExpect(jsonPath("$.products.size()", is(testUser.getProductDTOs().size())))
                .andExpect(jsonPath("$.first_name", is(testUser.getFirstName())))
                .andExpect(jsonPath("$.last_name", is(testUser.getLastName())))
                .andExpect(jsonPath("$.phone_number", is(testUser.getPhoneNumber())))
                .andExpect(jsonPath("$.creation_date", is(testUser.getCreationDate().toString())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost:8080/api/v1/users/1")))
                .andDo(document("users/getUserByUsername"));
    }

    @Test
    public void createNewUser() throws Exception {

        UserDTO user = userService.getUserById(testUser.getId()).getContent();

        user.setEmailAddress("dsadas@dsad.com");
        user.setUsername("New userames");

        userService.patchUser(testUser.getId(), user);

        mockMvc.perform(MockMvcRequestBuilders.post(USERS_BASIC_URL)
                .accept(MediaTypes.HAL_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document("users/createNewUser"));
    }

    @Test
    public void updateUser() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.put(USERS_BASIC_URL +
                "/" + testUser.getId())
                .accept(MediaTypes.HAL_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("users/updateUser"));
    }

    @Test
    public void patchUser()throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch(USERS_BASIC_URL +
                "/" + testUser.getId())
                .accept(MediaTypes.HAL_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testUser))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("users/patchUser"));
    }

    @Test
    public void deleteUserById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(USERS_BASIC_URL + "/" + testUser.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(document("users/deleteUserById"));
    }

    @Test
    public void getOrder() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(USERS_BASIC_URL + "/" + testUser.getId() + "/order")
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(testUser.getOrderDTO().getId().intValue())))
                .andDo(document("users/getOrder"));
    }

    @Test
    public void addNewOrder() throws Exception {

        orderService.deleteOrderById(testUser.getOrderDTO().getId());

        mockMvc.perform(MockMvcRequestBuilders.post(USERS_BASIC_URL +
                "/" + testUser.getId() + "/order")
                .accept(MediaTypes.HAL_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testUser.getOrderDTO()))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document("users/addNewOrder"));
    }

    @Test
    public void getAllUsersProducts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(USERS_BASIC_URL +  "/" + testUser.getId() + "/products")
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._embedded.products[0].id",
                        is(testUser.getProductDTOs().get(0).getId().intValue())))
                .andDo(document("users/getAllUsersProducts"));
    }

    @Test
    public void addNewProductToUser() throws Exception {

        productService.deleteProductById(testUser.getProductDTOs().get(0).getId());

        mockMvc.perform(MockMvcRequestBuilders.post(USERS_BASIC_URL +
                "/" + testUser.getId() + "/products")
                .accept(MediaTypes.HAL_JSON)
                .content(new ObjectMapper().writeValueAsBytes(testUser.getProductDTOs().get(0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(document("users/addNewProductToUser"));
    }
}