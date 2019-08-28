package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
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
import org.springframework.hateoas.Resource;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public void getShoppingCartById() {

        //given
        Mockito.when(shoppingCartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCart));

        //when
        Resource<ShoppingCartDTO> shoppingCartDTO = shoppingCartServiceImpl.getShoppingCartById(ID);

        //than
        assertNotNull(shoppingCartDTO);
        assertNotNull(shoppingCartDTO.getContent().getShoppingCartItemDTOs());
        assertNotNull(shoppingCartDTO.getContent().getUserDTO());

        assertEquals(shoppingCart.getId(), shoppingCartDTO.getContent().getId());
        assertEquals(shoppingCart.getCreationDate(), shoppingCartDTO.getContent().getCreationDate());
        assertEquals(shoppingCart.getCartStatus(), shoppingCartDTO.getContent().getCartStatus());

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }


    @Test
    public void deleteShoppingCartById() {

        //when
        shoppingCartRepository.deleteById(ID);

        Mockito.verify(shoppingCartRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}