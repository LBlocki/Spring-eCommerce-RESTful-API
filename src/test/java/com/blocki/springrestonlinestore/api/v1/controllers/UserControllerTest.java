package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import com.blocki.springrestonlinestore.core.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@WebMvcTest(UserController.class)
public class UserControllerTest {

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

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @InjectMocks
    UserController userController;

    private User user = new User();
    private Product product = new Product();
    private ShoppingCart shoppingCart = new ShoppingCart();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ExceptionController()).build();

        user = new User();
        user.setId(ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setAddress(ADDRESS);
        user.setCountry(COUNTRY);
        user.setPhoneNumber(PHONE_NUMBER);
        user.setCreationDate(CREATION_DATE);
        user.setEmailAddress(EMAIL_ADDRESS);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setGender(GENDER);

        product.setId(ID);
        product.setUser(user);

        user.setProducts(Arrays.asList(product,product));

        shoppingCart.setCartStatus(ShoppingCart.CartStatus.ACTIVE);
        shoppingCart.setId(ID);
        shoppingCart.setCreationDate(LocalDate.now());
        shoppingCart.setShoppingCartItems(new ArrayList<>());

        user.setShoppingCart(shoppingCart);
    }

    @Test
    public void getAllUsers() throws Exception {

    }

    @Test
    public void getUserById() {
    }

    @Test
    public void getUserByUsername() {
    }

    @Test
    public void createNewUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void patchUser() {
    }

    @Test
    public void deleteUserById() {
    }

    @Test
    public void getShoppingCart() {
    }

    @Test
    public void addNewShoppingCart() {
    }

    @Test
    public void getAllUsersProducts() {
    }

    @Test
    public void addNewProductToUser() {
    }
}