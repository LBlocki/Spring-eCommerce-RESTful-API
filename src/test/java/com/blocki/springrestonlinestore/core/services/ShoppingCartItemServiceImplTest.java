package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartItemMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemListDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartItemServiceImplTest {

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @InjectMocks
    private ShoppingCartItemServiceImpl shoppingCartItemServiceImpl;

    private final ShoppingCartItemMapper shoppingCartItemConverter = Mappers.getMapper(ShoppingCartItemMapper.class);

    private static final Long ID = 2L;
    private static final Integer quantity = 200;
    private ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder().id(ID).quantity(quantity).build();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllShoppingCartItems() {

        //given
        List<ShoppingCartItem> shoppingCartItems = Arrays.asList(new ShoppingCartItem(), new ShoppingCartItem());
        Mockito.when(shoppingCartItemRepository.findAll()).thenReturn(shoppingCartItems);

        //when
        ShoppingCartItemListDTO productListDTO = shoppingCartItemServiceImpl.getAllShoppingCartItems();

        //than
        assertNotNull(productListDTO);

        Mockito.verify(shoppingCartItemRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getShoppingCartItemById() {

        //given
        Mockito.when(shoppingCartItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCartItem));

        //when
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartItemServiceImpl.getShoppingCartItemById(ID);

        //than
        assertNotNull(shoppingCartItemDTO);
        assertEquals(shoppingCartItemDTO.getId(), shoppingCartItem.getId());
        assertEquals(shoppingCartItemDTO.getQuantity(), shoppingCartItem.getQuantity());

        Mockito.verify(shoppingCartItemRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void saveShoppingCartItem() {

        //given
        Mockito.when(shoppingCartItemRepository.save(Mockito.any())).thenReturn(shoppingCartItem);

        //when
        ShoppingCartItemDTO savedShoppingCartItemDTO = shoppingCartItemServiceImpl
                .saveShoppingCartItem(shoppingCartItemConverter.ShoppingCartItemToShoppingCartItemDTO(shoppingCartItem));

        //than
        assertNotNull(savedShoppingCartItemDTO);
        assertEquals(savedShoppingCartItemDTO.getId(), shoppingCartItem.getId());
        assertEquals(savedShoppingCartItemDTO.getQuantity(), shoppingCartItem.getQuantity());

        Mockito.verify(shoppingCartItemRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void createNewShoppingCartItem() {

        //given
        Mockito.when(shoppingCartItemRepository.save(Mockito.any())).thenReturn(shoppingCartItem);

        //when
        ShoppingCartItemDTO savedShoppingCartItemDTO = shoppingCartItemServiceImpl
                .createNewShoppingCartItem(shoppingCartItemConverter.ShoppingCartItemToShoppingCartItemDTO(shoppingCartItem));

        //than
        assertNotNull(savedShoppingCartItemDTO);
        assertEquals(savedShoppingCartItemDTO.getId(), shoppingCartItem.getId());
        assertEquals(savedShoppingCartItemDTO.getQuantity(), shoppingCartItem.getQuantity());

        Mockito.verify(shoppingCartItemRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void deleteShoppingCartItemById() {

        //when
        shoppingCartItemServiceImpl.deleteShoppingCartItemById(ID);

        //than
        Mockito.verify(shoppingCartItemRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}