package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

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
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User fixedUser;
    private UserDTO fixedUserDTO;

    @Before
    public void setUp() {

        fixedUser = User.builder()
                .firstName(FIRST_NAME)
                .id(ID)
                .lastName(LAST_NAME)
                .emailAddress(EMAIL_ADDRESS)
                .password(PASSWORD)
                .gender(GENDER)
                .creationDate(CREATION_DATE)
                .address(ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .country(COUNTRY)
                .username(USERNAME)
                .products(new ArrayList<>())
                .shoppingCart(new ShoppingCart())
                .build();


        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsers() {

        //given
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(fixedUser,fixedUser,fixedUser));

        //when
        Resources<Resource<UserDTO>> usersDTO = userServiceImpl.getAllUsers();

        //than
        assertNotNull(usersDTO);
        assertThat(usersDTO.getContent().size(), Matchers.equalTo(3));

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getUserById() {

        //given
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fixedUser));

        //when
        Resource<UserDTO> userDTO = userServiceImpl.getUserById(ID);

        //than
        assertNotNull(userDTO);
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
    public void createNewShoppingCart() {
    }

    @Test
    public void createNewProduct() {
    }

    @Test
    public void getAllProducts() {
    }

    @Test
    public void getShoppingCart() {
    }

}