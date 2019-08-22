package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartMapperTest {

    private ShoppingCartMapper shoppingCartConverter = ShoppingCartMapper.INSTANCE;

    private static final Long shoppingCartID = 2L;
    private static final LocalDate creationDate = LocalDate.of(2000, 12, 12);
    private static final ShoppingCart.CartStatus cartStatus = ShoppingCart.CartStatus.ACTIVE;
    private static final String shoppingCartUrl = "/api/v1/shoppingCartItems";

    @Test
    public void ShoppingCartToShoppingCartDTO() {

        ShoppingCart shoppingCart = ShoppingCart.builder()
        .id(shoppingCartID)
        .creationDate(creationDate)
        .cartStatus(cartStatus)
        .user(new User())
        .shoppingCartItems(new ArrayList<>())
        .build();


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
        shoppingCartDTO.setShoppingCartUrl(shoppingCartUrl);

        ShoppingCart shoppingCart = shoppingCartConverter.shoppingCartDTOToShoppingCart(shoppingCartDTO);

        assertNotNull(shoppingCart);
        assertNotNull(shoppingCart.getUser());
        assertNotNull(shoppingCart.getShoppingCartItems());

        assertEquals(shoppingCartDTO.getCartStatus(), shoppingCart.getCartStatus());
        assertEquals(shoppingCartDTO.getCreationDate(), shoppingCart.getCreationDate());
        assertEquals(shoppingCartDTO.getId(), shoppingCart.getId());
    }
}