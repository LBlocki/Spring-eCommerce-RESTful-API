package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartMapperTest {

    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);

    private static final Long shoppingCartID = 2L;
    private static final LocalDate creationDate = LocalDate.of(2000, 12, 12);
    private static final ShoppingCart.CartStatus cartStatus = ShoppingCart.CartStatus.ACTIVE;

    @Test
    public void ShoppingCartToShoppingCartDTO() {

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartID);
        shoppingCart.setCreationDate(creationDate);
        shoppingCart.setCartStatus(cartStatus);
        shoppingCart.setUser(new User());
        shoppingCart.setShoppingCartItems(new ArrayList<>());


        ShoppingCartDTO shoppingCartDTO = shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart);

        assertNotNull(shoppingCartDTO);
        assertNotNull(shoppingCartDTO.getUserDTO());
        assertNotNull(shoppingCartDTO.getShoppingCartItemDTOs());

        assertEquals(shoppingCartDTO.getCartStatus(), shoppingCart.getCartStatus());
        assertEquals(shoppingCartDTO.getCreationDate(), shoppingCart.getCreationDate());
        assertEquals(shoppingCartDTO.getId(), shoppingCart.getId());
    }

    @Test
    public void ShoppingCartDTOToShoppingCart() {

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(shoppingCartID);
        shoppingCartDTO.setCreationDate(creationDate);
        shoppingCartDTO.setCartStatus(cartStatus);
        shoppingCartDTO.setUserDTO(new UserDTO());
        shoppingCartDTO.setShoppingCartItemDTOs(new ArrayList<>());

        ShoppingCart shoppingCart = shoppingCartConverter.shoppingCartDTOToShoppingCart(shoppingCartDTO);

        assertNotNull(shoppingCart);
        assertNotNull(shoppingCart.getUser());
        assertNotNull(shoppingCart.getShoppingCartItems());

        assertEquals(shoppingCartDTO.getCartStatus(), shoppingCart.getCartStatus());
        assertEquals(shoppingCartDTO.getCreationDate(), shoppingCart.getCreationDate());
        assertEquals(shoppingCartDTO.getId(), shoppingCart.getId());
    }
}