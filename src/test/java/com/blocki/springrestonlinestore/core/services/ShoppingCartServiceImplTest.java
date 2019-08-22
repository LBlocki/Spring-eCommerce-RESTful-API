package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartListDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    private ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);

    private ShoppingCart shoppingCart;

    private static final Long ID = 2L;
    private static final LocalDate creationDate = LocalDate.now();
    private static final ShoppingCart.CartStatus cartStatus = ShoppingCart.CartStatus.ACTIVE;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        shoppingCart = ShoppingCart.builder().id(ID).cartStatus(cartStatus).creationDate(creationDate).shoppingCartItems( new ArrayList<>()).user(new User()).build();
    }

    @Test
    public void getAllShoppingCarts() {

        //given
        List<ShoppingCart> shoppingCart = Arrays.asList(new ShoppingCart(), new ShoppingCart());
        Mockito.when(shoppingCartRepository.findAll()).thenReturn(shoppingCart);

        //when
       ShoppingCartListDTO shoppingCartListDTO = shoppingCartServiceImpl.getAllShoppingCarts();

       //than
        assertNotNull(shoppingCartListDTO);
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getShoppingCartById() {

        //given
        Mockito.when(shoppingCartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCart));

        //when
        ShoppingCartDTO shoppingCartDTO = shoppingCartServiceImpl.getShoppingCartById(ID);

        //than
        assertNotNull(shoppingCartDTO);
        assertNotNull(shoppingCartDTO.getShoppingCartItemDTOs());
        assertNotNull(shoppingCartDTO.getUserDTO());

        assertEquals(shoppingCart.getId(), shoppingCartDTO.getId());
        assertEquals(shoppingCart.getCreationDate(), shoppingCartDTO.getCreationDate());
        assertEquals(shoppingCart.getCartStatus(), shoppingCartDTO.getCartStatus());

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void createNewShoppingCart() {

        //given
        Mockito.when(shoppingCartRepository.save(Mockito.any(ShoppingCart.class))).thenReturn(shoppingCart);

        //when
        ShoppingCartDTO shoppingCartDTO = shoppingCartServiceImpl.createNewShoppingCart(shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart));

        //than
        assertNotNull(shoppingCartDTO);
        assertNotNull(shoppingCartDTO.getUserDTO());
        assertNotNull(shoppingCartDTO.getShoppingCartItemDTOs());

        assertEquals(shoppingCart.getId(), shoppingCartDTO.getId());
        assertEquals(shoppingCart.getCartStatus(), shoppingCartDTO.getCartStatus());
        assertEquals(shoppingCart.getCreationDate(), shoppingCartDTO.getCreationDate());

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).save(Mockito.any(ShoppingCart.class));

    }

    @Test
    public void deleteShoppingCartById() {

        //when
        shoppingCartRepository.deleteById(ID);

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}