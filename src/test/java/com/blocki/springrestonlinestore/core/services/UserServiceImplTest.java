package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.TestEntity;
import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    private final TestEntity testEntity = new TestEntity();

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);

    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);

    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);

    private User user;

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

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        user = testEntity.getUser();
    }

    @Test
    public void getAllUsers() {

        //given
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user, user, user));

        //when
        Resources<Resource<UserDTO>> testUsersList = userServiceImpl.getAllUsers();

        //then
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(userResourceAssembler, Mockito.times(3))
                .toResource(Mockito.any(UserDTO.class));

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(userResourceAssembler);

        assertNotNull(testUsersList);
        assertThat(testUsersList.getContent().size(), Matchers.equalTo(3));
    }

    @Test
    public void getUserById() {

        //given
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        //when
        Resource<UserDTO> testUserDTO = userServiceImpl.getUserById(user.getId());

        //then
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(userResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(UserDTO.class));

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(userResourceAssembler);

        assertNotNull(testUserDTO);
        assertNotNull(testUserDTO.getContent().getShoppingCartDTO());
        assertNotNull(testUserDTO.getContent().getProductDTOs());

        assertEquals(testUserDTO.getContent().getId(), user.getId());
        assertEquals(testUserDTO.getContent().getProductDTOs().size(), user.getProducts().size());
        assertEquals(testUserDTO.getContent().getShoppingCartDTO().getId(), user.getShoppingCart().getId());
        assertEquals(testUserDTO.getContent().getFirstName(), user.getFirstName());
        assertEquals(testUserDTO.getContent().getLastName(), user.getLastName());
        assertEquals(testUserDTO.getContent().getAddress(), user.getAddress());
        assertEquals(testUserDTO.getContent().getCountry(), user.getCountry());
        assertEquals(testUserDTO.getContent().getCreationDate(), user.getCreationDate());
        assertEquals(testUserDTO.getContent().getEmailAddress(), user.getEmailAddress());
        assertEquals(testUserDTO.getContent().getGender(), user.getGender());
        assertEquals(testUserDTO.getContent().getPhoneNumber(), user.getPhoneNumber());
        assertEquals(testUserDTO.getContent().getUsername(), user.getUsername());

        assertArrayEquals(testUserDTO.getContent().getPassword(), user.getPassword());
    }

    @Test
    public void createNewUser() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        //when
        Resource<UserDTO> testSavedUserDTO = userServiceImpl.createNewUser(userConverter.userToUserDTO(user));

        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(userResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(UserDTO.class));

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(userResourceAssembler);

        assertNotNull(testSavedUserDTO);
        assertNotNull(testSavedUserDTO.getContent().getShoppingCartDTO());
        assertNotNull(testSavedUserDTO.getContent().getProductDTOs());

        assertEquals(testSavedUserDTO.getContent().getId(), user.getId());
        assertEquals(testSavedUserDTO.getContent().getProductDTOs().size(), user.getProducts().size());
        assertEquals(testSavedUserDTO.getContent().getShoppingCartDTO().getId(), user.getShoppingCart().getId());
        assertEquals(testSavedUserDTO.getContent().getFirstName(), user.getFirstName());
        assertEquals(testSavedUserDTO.getContent().getLastName(), user.getLastName());
        assertEquals(testSavedUserDTO.getContent().getAddress(), user.getAddress());
        assertEquals(testSavedUserDTO.getContent().getCountry(), user.getCountry());
        assertEquals(testSavedUserDTO.getContent().getCreationDate(), user.getCreationDate());
        assertEquals(testSavedUserDTO.getContent().getEmailAddress(), user.getEmailAddress());
        assertEquals(testSavedUserDTO.getContent().getGender(), user.getGender());
        assertEquals(testSavedUserDTO.getContent().getPhoneNumber(), user.getPhoneNumber());
        assertEquals(testSavedUserDTO.getContent().getUsername(), user.getUsername());

        assertArrayEquals(testSavedUserDTO.getContent().getPassword(), user.getPassword());
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void createNewUserTestExistingResourceException() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        //when
        userServiceImpl.createNewUser(userConverter.userToUserDTO(user));

        //then
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(userResourceAssembler, Mockito.times(0))
                .toResource(Mockito.any(UserDTO.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(userResourceAssembler);
    }

    @Test
    public void deleteUserById() {

        //when
        userServiceImpl.deleteUserById(user.getId());

        //then
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void createNewShoppingCart() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        ShoppingCart shoppingCart = user.getShoppingCart();
        user.setShoppingCart(null);

        //when
        Resource<ShoppingCartDTO> testCreatedShoppingCartDTO = userServiceImpl.createNewShoppingCart(user.getId(),
                shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart));

        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(shoppingCartResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(ShoppingCartDTO.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(shoppingCartResourceAssembler);

        assertNotNull(testCreatedShoppingCartDTO);
        assertNotNull(testCreatedShoppingCartDTO.getContent().getUserDTO());
        assertNotNull(testCreatedShoppingCartDTO.getContent().getShoppingCartItemDTOs());

        assertEquals(testCreatedShoppingCartDTO.getContent().getUserDTO().getId(), shoppingCart.getUser().getId());
        assertEquals(testCreatedShoppingCartDTO.getContent().getId(), shoppingCart.getId());
        assertEquals(testCreatedShoppingCartDTO.getContent().getUserDTOId(), shoppingCart.getUser().getId());
        assertEquals(testCreatedShoppingCartDTO.getContent().getShoppingCartItemDTOs().size(),
                shoppingCart.getShoppingCartItems().size());
        assertEquals(testCreatedShoppingCartDTO.getContent().getCartStatus(), shoppingCart.getCartStatus());
        assertEquals(testCreatedShoppingCartDTO.getContent().getCreationDate(), shoppingCart.getCreationDate());
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void createNewShoppingCartWhenOneAlreadyExists() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        //when
        userServiceImpl.createNewShoppingCart(user.getId(), shoppingCartConverter
               .shoppingCartToShoppingCartDTO(user.getShoppingCart()));

        //then
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(userResourceAssembler, Mockito.times(0))
                .toResource(Mockito.any(UserDTO.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(userResourceAssembler);
    }

    @Test
    public void createNewProduct() {

        //given
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Product product = user.getProducts().get(0);
        user.setProducts(new ArrayList<>());

        //when
        Resource<ProductDTO> testCreatedProductDTO = userServiceImpl.createNewProduct
                (user.getId(),productConverter.productToProductDTO(product));

        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(productResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(ProductDTO.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(productResourceAssembler);

        assertNotNull(testCreatedProductDTO);
        assertNotNull(testCreatedProductDTO.getContent().getUserDTO());
        assertNotNull(testCreatedProductDTO.getContent().getCategoryDTO());

        assertEquals(testCreatedProductDTO.getContent().getUserDTO().getId(), product.getUser().getId());
        assertEquals(testCreatedProductDTO.getContent().getId(), product.getId());
        assertEquals(testCreatedProductDTO.getContent().getName(), product.getName());
        assertEquals(testCreatedProductDTO.getContent().getDescription(), product.getDescription());
        assertEquals(testCreatedProductDTO.getContent().getCreationDate(), product.getCreationDate());
        assertEquals(testCreatedProductDTO.getContent().getProductStatus(), product.getProductStatus());
        assertEquals(testCreatedProductDTO.getContent().getCost(), product.getCost());
        assertEquals(testCreatedProductDTO.getContent().getCategoryDTO().getId(), product.getCategory().getId());
        assertEquals(testCreatedProductDTO.getContent().getUserDTOId(), product.getUser().getId());

        assertArrayEquals(testCreatedProductDTO.getContent().getPhoto(), product.getPhoto());
    }

    @Test
    public void getAllProducts() {

        //given
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        //when
        Resources<Resource<ProductDTO>> testProductsList = userServiceImpl.getAllProducts(user.getId());

        //then
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productResourceAssembler, Mockito.times(2))
                .toResource(Mockito.any(ProductDTO.class));

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(productResourceAssembler);

        assertNotNull(testProductsList);
        assertThat(testProductsList.getContent().size(), Matchers.equalTo(2));
    }

    @Test
    public void getShoppingCartById() {

        //given
        ShoppingCart shoppingCart = user.getShoppingCart();
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        //when
        Resource<ShoppingCartDTO> testShoppingCartDTO = userServiceImpl.getShoppingCartById(user.getId());

        //then
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(shoppingCartResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(ShoppingCartDTO.class));

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(shoppingCartResourceAssembler);

        assertNotNull(testShoppingCartDTO);
        assertNotNull(testShoppingCartDTO.getContent().getUserDTO());
        assertNotNull(testShoppingCartDTO.getContent().getShoppingCartItemDTOs());

        assertEquals(testShoppingCartDTO.getContent().getId(), shoppingCart.getId());
        assertEquals(testShoppingCartDTO.getContent().getUserDTO().getId(), shoppingCart.getUser().getId());
        assertEquals(testShoppingCartDTO.getContent().
                getShoppingCartItemDTOs().size(), shoppingCart.getShoppingCartItems().size());
        assertEquals(testShoppingCartDTO.getContent().getCreationDate(), shoppingCart.getCreationDate());
        assertEquals(testShoppingCartDTO.getContent().getUserDTOId(), shoppingCart.getUser().getId());
        assertEquals(testShoppingCartDTO.getContent().getCartStatus(), shoppingCart.getCartStatus());
    }

    @Test
    public void getUserByUsername() {

        //given
        Mockito.when(userRepository.findUserByUsername(Mockito.any())).thenReturn(Optional.of(user));

        //when
        Resource<UserDTO> testUserDTO = userServiceImpl.getUserByUsername(user.getUsername());

        //than
        Mockito.verify(userRepository, Mockito.times(1)).findUserByUsername(Mockito.any());
        Mockito.verify(userResourceAssembler, Mockito.times(1)).toResource(Mockito.any(UserDTO.class));

        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoMoreInteractions(userResourceAssembler);

        assertNotNull(testUserDTO);
        assertNotNull(testUserDTO.getContent().getShoppingCartDTO());
        assertNotNull(testUserDTO.getContent().getProductDTOs());

        assertEquals(testUserDTO.getContent().getId(), user.getId());
        assertEquals(testUserDTO.getContent().getProductDTOs().size(), user.getProducts().size());
        assertEquals(testUserDTO.getContent().getShoppingCartDTO().getId(), user.getShoppingCart().getId());
        assertEquals(testUserDTO.getContent().getFirstName(), user.getFirstName());
        assertEquals(testUserDTO.getContent().getLastName(), user.getLastName());
        assertEquals(testUserDTO.getContent().getAddress(), user.getAddress());
        assertEquals(testUserDTO.getContent().getCountry(), user.getCountry());
        assertEquals(testUserDTO.getContent().getCreationDate(), user.getCreationDate());
        assertEquals(testUserDTO.getContent().getEmailAddress(), user.getEmailAddress());
        assertEquals(testUserDTO.getContent().getGender(), user.getGender());
        assertEquals(testUserDTO.getContent().getPhoneNumber(), user.getPhoneNumber());
        assertEquals(testUserDTO.getContent().getUsername(), user.getUsername());

        assertArrayEquals(testUserDTO.getContent().getPassword(), user.getPassword());
    }
}