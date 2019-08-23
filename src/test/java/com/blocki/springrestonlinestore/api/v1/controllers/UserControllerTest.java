package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.*;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

public class UserControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private static final Long ID = 2L;
    private static final String FIRST_NAME = "Sherlock";
    private static final String LAST_NAME = "Holmes";
    private static final String EMAIL_ADDRESS = "emailAdress@gmail.com";
    private static final char[] PASSWORD = {'s','s','s','s','s','s','s'};
    private static final User.Gender GENDER = User.Gender.MALE;
    private static final LocalDate CREATION_DATE = LocalDate.now();
    private static final String ADDRESS = "221B Baker Street";
    private static final String PHONE_NUMBER = "123456789";
    private static final String COUNTRY = "Poland";
    private static final String USERNAME = "GreatUser";
    private static final String USER_URL = UserController.USER_CONTROLLER_BASIC_URL + "/" + ID;

    private static final String SHOPPING_CART_URL = USER_URL + "/shoppingCarts";
    private static final ShoppingCart.CartStatus CART_STATUS = ShoppingCart.CartStatus.ACTIVE;

    private static final String DESCRIPTION = "This is description";
    private static final String PRODUCT_NAME = "Name of the product";
    private static final Byte[] PHOTO = {'s'};
    private static final Product.ProductStatus PRODUCT_STATUS = Product.ProductStatus.AVALIABLE;
    private static final CategoryDTO CATEGORY_DTO = new CategoryDTO();
    private static final String PRODUCT_URL = USER_URL + "/products";


    private UserDTO userDTO = new UserDTO();
    private ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
    private ProductDTO productDTO = new ProductDTO();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        userDTO.setFirstName(FIRST_NAME);
        userDTO.setId(ID);
        userDTO.setLastName(LAST_NAME);
        userDTO.setEmailAddress(EMAIL_ADDRESS);
        userDTO.setPassword(PASSWORD);
        userDTO.setGender(GENDER);
        userDTO.setCreationDate(CREATION_DATE);
        userDTO.setAddress(ADDRESS);
        userDTO.setPhoneNumber(PHONE_NUMBER);
        userDTO.setCountry(COUNTRY);
        userDTO.setUsername(USERNAME);
        userDTO.setUserUrl(USER_URL);

        shoppingCartDTO.setUserDTO(userDTO);
        shoppingCartDTO.setShoppingCartUrl(SHOPPING_CART_URL);
        shoppingCartDTO.setCartStatus(CART_STATUS);
        shoppingCartDTO.setCreationDate(CREATION_DATE);
        shoppingCartDTO.setId(ID);

        productDTO.setUserDTO(userDTO);
        productDTO.setCost(BigDecimal.ONE);
        productDTO.setCreationDate(CREATION_DATE);
        productDTO.setDescription(DESCRIPTION);
        productDTO.setId(ID);
        productDTO.setName(PRODUCT_NAME);
        productDTO.setPhoto(PHOTO);
        productDTO.setProductStatus(PRODUCT_STATUS);
        productDTO.setCategoryDTO(CATEGORY_DTO);
        productDTO.setProductUrl(PRODUCT_URL);

    }

    @Test
    public void getListOfUsers() throws  Exception {

        //given
        UserListDTO userDTOs = new UserListDTO(Collections.singletonList(userDTO));

        Mockito.when(userService.getAllUsers()).thenReturn(userDTOs);

        //than
        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users", Matchers.hasSize(1)))
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL));

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
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL + "/" + userDTO.getId()));

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
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL));

        Mockito.verify(userService, Mockito.times(1)).createNewUser(Mockito.any(UserDTO.class));
    }

    @Test
    public void updateUser() throws Exception{

        //given
        Mockito.when(userService.updateUser(Mockito.anyLong(), Mockito.any(UserDTO.class))).thenReturn(userDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders.put(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId()));

        Mockito.verify(userService, Mockito.times(1)).updateUser(Mockito.anyLong(), Mockito.any(UserDTO.class));

    }

    @Test
    public void patchUser() throws Exception{

        //given
        Mockito.when(userService.patchUser(Mockito.anyLong(), Mockito.any(UserDTO.class))).thenReturn(userDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders.patch(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId()));

        Mockito.verify(userService, Mockito.times(1)).patchUser(Mockito.anyLong(), Mockito.any(UserDTO.class));
    }

    @Test
    public void deleteUserById() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete(UserController.USER_CONTROLLER_BASIC_URL + "/" + userDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId()));

        Mockito.verify(userService, Mockito.times(1)).deleteUserById(Mockito.any());
    }


    @Test
    public void getListOfAllShoppingCarts() throws Exception {

        Mockito.when(userService.getAllShoppingCarts(Mockito.anyLong()))
                .thenReturn(new ShoppingCartListDTO(Collections.singletonList(shoppingCartDTO)));

        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL + "/" + userDTO.getId() + "/" + "shoppingCarts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shopping_carts", Matchers.hasSize(1)))
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId()+ "/shoppingCarts"));
    }

    @Test
    public void addNewShoppingCart() throws Exception {

        Mockito.when(userService.createNewShoppingCart(Mockito.anyLong(),Mockito.any(ShoppingCartDTO.class)))
                .thenReturn(shoppingCartDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(UserController.USER_CONTROLLER_BASIC_URL + "/" + userDTO.getId() + "/" + "shoppingCarts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(shoppingCartDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo((shoppingCartDTO.getId().intValue()))))
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId() + "/shoppingCarts"));
    }

    @Test
    public void getListOfAllUsersProducts() throws Exception{

        Mockito.when(userService.getAllProducts(Mockito.anyLong()))
                .thenReturn(new ProductListDTO(Collections.singletonList(productDTO)));

        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL + "/" + userDTO.getId() + "/" + "products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products", Matchers.hasSize(1)))
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId() + "/products"));
    }

    @Test
    public void addNewProductToUser()throws Exception {

        Mockito.when(userService.createNewProduct(Mockito.anyLong(),Mockito.any(ProductDTO.class)))
                .thenReturn(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(UserController.USER_CONTROLLER_BASIC_URL + "/" + userDTO.getId() + "/" + "products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo((shoppingCartDTO.getId().intValue()))))
                .andDo(document(UserController.USER_CONTROLLER_BASIC_URL + "/" +  userDTO.getId() + "/products"));
    }
}