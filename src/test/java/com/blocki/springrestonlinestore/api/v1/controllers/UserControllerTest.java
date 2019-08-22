package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.*;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private static final Long ID = 2L;
    private static final String firstName = "Michael";

    private UserDTO userDTO = new UserDTO();
    private ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
    private ProductDTO productDTO = new ProductDTO();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDTO.setFirstName(firstName);
        userDTO.setId(ID);
        userDTO.setLastName("sda");
        userDTO.setEmailAddress("dsa@wew.cs");
        userDTO.setPassword(new char[]{'s','s','s','s','s','s','s'});
        userDTO.setGender(User.Gender.MALE);
        userDTO.setCreationDate(LocalDate.MAX);
        userDTO.setAddress("dsa");
        userDTO.setPhoneNumber("2312312312");
        userDTO.setCountry("dsa");
        userDTO.setUsername("sda");
        userDTO.setUserUrl(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID);

        shoppingCartDTO.setUserDTO(userDTO);
        shoppingCartDTO.setShoppingCartUrl("dsa");
        shoppingCartDTO.setCartStatus(ShoppingCart.CartStatus.ACTIVE);
        shoppingCartDTO.setCreationDate(LocalDate.now());
        shoppingCartDTO.setId(ID);

        productDTO.setUserDTO(userDTO);
        productDTO.setCost(BigDecimal.ONE);
        productDTO.setCreationDate(LocalDate.now());
        productDTO.setDescription("sdasdsa");
        productDTO.setId(ID);
        productDTO.setName("dsa");
        productDTO.setPhoto(new Byte[]{'d'});
        productDTO.setProductStatus(Product.ProductStatus.AVALIABLE);
        productDTO.setCategoryDTO(new CategoryDTO());
        productDTO.setProductUrl("dsa");

    }

    @Test
    public void getListOfUsers() throws  Exception {

        //given
        UserListDTO userDTOs = new UserListDTO( Arrays.asList(new UserDTO(), new UserDTO()));

        Mockito.when(userService.getAllUsers()).thenReturn(userDTOs);

        //than
        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users", Matchers.hasSize(2)));

        Mockito.verify(userService, Mockito.times(1)).getAllUsers();

    }

    @Test
    public void getUserById() throws Exception {

        //given
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(userDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL + "/" + userDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.equalTo(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_url", Matchers.equalTo(userDTO.getUserUrl())));

        Mockito.verify(userService, Mockito.times(1)).getUserById(Mockito.anyLong());
    }

    @Test
    public void createNewUser() throws Exception {

        //given
        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(userDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders.post(UserController.USER_CONTROLLER_BASIC_URL )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.equalTo(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_url", Matchers.equalTo(userDTO.getUserUrl())));

        Mockito.verify(userService, Mockito.times(1)).createNewUser(Mockito.any(UserDTO.class));
    }

    @Test
    public void updateUser() throws Exception{

        //given
        Mockito.when(userService.updateUser(Mockito.anyLong(), Mockito.any(UserDTO.class))).thenReturn(userDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders.put(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.equalTo(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_url", Matchers.equalTo(userDTO.getUserUrl())));

        Mockito.verify(userService, Mockito.times(1)).updateUser(Mockito.anyLong(), Mockito.any(UserDTO.class));

    }

    @Test
    public void patchUser() throws Exception{

        //given
        Mockito.when(userService.patchUser(Mockito.anyLong(), Mockito.any(UserDTO.class))).thenReturn(userDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders.patch(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.equalTo(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_url", Matchers.equalTo(userDTO.getUserUrl())));

        Mockito.verify(userService, Mockito.times(1)).patchUser(Mockito.anyLong(), Mockito.any(UserDTO.class));
    }

    @Test
    public void deleteUserById() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).deleteUserById(Mockito.any());
    }


    @Test
    public void getListOfAllShoppingCarts() throws Exception {

        Mockito.when(userService.getAllShoppingCarts(Mockito.anyLong()))
                .thenReturn(new ShoppingCartListDTO(Collections.singletonList(shoppingCartDTO)));

        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID + "/" + "shoppingCarts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shopping_carts", Matchers.hasSize(1)));
    }

    @Test
    public void addNewShoppingCart() throws Exception {

        Mockito.when(userService.createNewShoppingCart(Mockito.anyLong(),Mockito.any(ShoppingCartDTO.class)))
                .thenReturn(shoppingCartDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID + "/" + "shoppingCarts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shoppingCartDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo((shoppingCartDTO.getId().intValue()))));
    }

    @Test
    public void getListOfAllUsersProducts() throws Exception{

        Mockito.when(userService.getAllProducts(Mockito.anyLong()))
                .thenReturn(new ProductListDTO(Collections.singletonList(productDTO)));

        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID + "/" + "products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products", Matchers.hasSize(1)));
    }

    @Test
    public void addNewProductToUser()throws Exception {

        Mockito.when(userService.createNewProduct(Mockito.anyLong(),Mockito.any(ProductDTO.class)))
                .thenReturn(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID + "/" + "products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo((shoppingCartDTO.getId().intValue()))));
    }
}