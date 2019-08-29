package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ProductResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.UserResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.exceptions.ResourceAlreadyExistsException;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

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

    @Spy
    private UserResourceAssembler userResourceAssembler = new UserResourceAssembler();

    @Spy
    private ShoppingCartResourceAssembler shoppingCartResourceAssembler = new ShoppingCartResourceAssembler();

    @Spy
    private ProductResourceAssembler productResourceAssembler = new ProductResourceAssembler();

    @Spy
    private UserMapper userConverter = Mappers.getMapper(UserMapper.class);

    private User fixedUser;
    private UserDTO fixedUserDTO;

    private ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
    private ProductDTO productDTO = new ProductDTO();
    private Product product = new Product();

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
                .build();

        productDTO.setId(ID);
        productDTO.setUserDTO(fixedUserDTO);

        product.setId(ID);
        product.setUser(fixedUser);

        fixedUser.setProducts(Arrays.asList(product,product));

        fixedUserDTO = userConverter.userToUserDTO(fixedUser);

        shoppingCartDTO.setCartStatus(ShoppingCart.CartStatus.ACTIVE);
        shoppingCartDTO.setId(ID);
        shoppingCartDTO.setCreationDate(LocalDate.now());
        shoppingCartDTO.setShoppingCartItemDTOs(new ArrayList<>());

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsers() {

        //given
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(fixedUser,fixedUser,fixedUser));

        //when
        Resources<Resource<UserDTO>> usersList = userServiceImpl.getAllUsers();

        //than
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(userResourceAssembler, Mockito.times(3)).toResource(Mockito.any(UserDTO.class));

        assertNotNull(usersList);
        assertThat(usersList.getContent().size(), Matchers.equalTo(3));
    }

    @Test
    public void getUserById() {

        //given
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fixedUser));

        //when
        Resource<UserDTO> userDTO = userServiceImpl.getUserById(ID);

        //than
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(userResourceAssembler, Mockito.times(1)).toResource(Mockito.any(UserDTO.class));

        assertNotNull(userDTO);
        assertEquals(userDTO.getContent().getId(), fixedUser.getId());
        assertEquals(userDTO.getContent().getFirstName(), fixedUser.getFirstName());
    }

    @Test
    public void createNewUser() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(fixedUser);

        //when
        Resource<UserDTO> savedUserDTO = userServiceImpl.createNewUser(fixedUserDTO);

        //than
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(userResourceAssembler, Mockito.times(1)).toResource(Mockito.any(UserDTO.class));

        assertNotNull(savedUserDTO);
        assertEquals(savedUserDTO.getContent().getId(), fixedUser.getId());
        assertEquals(savedUserDTO.getContent().getFirstName(), fixedUser.getFirstName());
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void createNewUserTestExistingResourceException() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(fixedUser);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fixedUser));

        //when
        userServiceImpl.createNewUser(fixedUserDTO);

        //than
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(userResourceAssembler, Mockito.times(0)).toResource(Mockito.any(UserDTO.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void deleteUserById() {

        userServiceImpl.deleteUserById(ID);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void createNewShoppingCart() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(fixedUser);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fixedUser));

        //when
        Resource<ShoppingCartDTO> createdShoppingCartDTO = userServiceImpl.createNewShoppingCart(ID, shoppingCartDTO);

        //than
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(shoppingCartResourceAssembler, Mockito.times(1)).toResource(Mockito.any(ShoppingCartDTO.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());

        assertNotNull(createdShoppingCartDTO);
        assertEquals(createdShoppingCartDTO.getContent().getUserDTO().getId(), fixedUser.getId());
        assertEquals(createdShoppingCartDTO.getContent().getId(), shoppingCartDTO.getId());
    }

    @Test
    public void createNewProduct() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(fixedUser);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fixedUser));

        //when
        Resource<ProductDTO> createdProductDTO = userServiceImpl.createNewProduct(ID, productDTO);

        //than
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(productResourceAssembler, Mockito.times(1)).toResource(Mockito.any(ProductDTO.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());

        assertNotNull(createdProductDTO);
        assertEquals(createdProductDTO.getContent().getUserDTO().getId(), fixedUser.getId());
        assertEquals(createdProductDTO.getContent().getId(), productDTO.getId());
    }

    @Test
    public void getAllProducts() {

        //given
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fixedUser));

        //when
        Resources<Resource<ProductDTO>> productsList = userServiceImpl.getAllProducts(fixedUser.getId());

        //than
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productResourceAssembler, Mockito.times(2)).toResource(Mockito.any(ProductDTO.class));

        assertNotNull(productsList);
        assertThat(productsList.getContent().size(), Matchers.equalTo(2));
    }

    @Test
    public void getShoppingCart() {

        //given
        fixedUser.setShoppingCart(ShoppingCart.builder().user(fixedUser).id(ID).build());
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fixedUser));

        //when
        Resource<ShoppingCartDTO> gotShoppingCartDTO = userServiceImpl.getShoppingCart(ID);

        //than
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(shoppingCartResourceAssembler, Mockito.times(1)).toResource(Mockito.any(ShoppingCartDTO.class));

        assertNotNull(gotShoppingCartDTO);
        assertEquals(gotShoppingCartDTO.getContent().getId(), ID);
    }

    @Test
    public void getUserByUsername() {

        //given
        Mockito.when(userRepository.findUserByUsername(Mockito.any())).thenReturn(Optional.of(fixedUser));

        //when
        Resource<UserDTO> userDTO = userServiceImpl.getUserByUsername(USERNAME);

        //than
        Mockito.verify(userRepository, Mockito.times(1)).findUserByUsername(Mockito.any());
        Mockito.verify(userResourceAssembler, Mockito.times(1)).toResource(Mockito.any(UserDTO.class));

        assertNotNull(userDTO);
        assertEquals(userDTO.getContent().getId(), fixedUser.getId());
        assertEquals(userDTO.getContent().getFirstName(), fixedUser.getFirstName());
    }
}