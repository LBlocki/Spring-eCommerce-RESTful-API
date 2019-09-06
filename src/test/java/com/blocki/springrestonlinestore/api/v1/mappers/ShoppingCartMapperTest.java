package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.TestEntity;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartMapperTest {

    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);
    private final TestEntity testEntity = new TestEntity();

    private ShoppingCart shoppingCart;

    @Before
    public void setUp() {

        shoppingCart = testEntity.getShoppingCart();
    }

    @Test
    public void ShoppingCartToShoppingCartDTO() {

        //when
        ShoppingCartDTO testShoppingCartDTO = shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart);

        //then
        assertNotNull(testShoppingCartDTO);
        assertNotNull(testShoppingCartDTO.getUserDTO());
        assertNotNull(testShoppingCartDTO.getShoppingCartItemDTOs());

        assertEquals(testShoppingCartDTO.getCartStatus(), shoppingCart.getCartStatus());
        assertEquals(testShoppingCartDTO.getCreationDate(), shoppingCart.getCreationDate());
        assertEquals(testShoppingCartDTO.getId(), shoppingCart.getId());
        assertEquals(testShoppingCartDTO.getUserDTOId(), shoppingCart.getUser().getId());
        assertEquals(testShoppingCartDTO.getUserDTO().getId(), shoppingCart.getUser().getId());
        assertEquals(testShoppingCartDTO.getShoppingCartItemDTOs().size(), shoppingCart.getShoppingCartItems().size());
    }

    @Test
    public void ShoppingCartDTOToShoppingCart() {

        //given
        ShoppingCartDTO shoppingCartDTO = shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart);

        //when
        ShoppingCart testShoppingCart = shoppingCartConverter.shoppingCartDTOToShoppingCart(shoppingCartDTO);

        //then
        assertNotNull(testShoppingCart);
        assertNotNull(testShoppingCart.getUser());
        assertNotNull(testShoppingCart.getShoppingCartItems());

        assertEquals(testShoppingCart.getCartStatus(), shoppingCartDTO.getCartStatus());
        assertEquals(testShoppingCart.getCreationDate(), shoppingCartDTO.getCreationDate());
        assertEquals(testShoppingCart.getId(), shoppingCartDTO.getId());
        assertEquals(testShoppingCart.getUser().getId(), shoppingCartDTO.getUserDTO().getId());
        assertEquals(testShoppingCart.getShoppingCartItems().size(), shoppingCartDTO.getShoppingCartItemDTOs().size());
    }
}