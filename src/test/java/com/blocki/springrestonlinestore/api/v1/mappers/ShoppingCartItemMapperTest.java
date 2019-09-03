package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartItemMapperTest {

    private ShoppingCartItemMapper shoppingCartItemConverter = Mappers.getMapper(ShoppingCartItemMapper.class);

    private static final Long shoppingCartItemId = 2L;
    private static final Integer quantity = 10;
    private static final BigDecimal totalCost = BigDecimal.valueOf(30);

    private Product product = new Product();
    private  ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

    @Before
    public void setUp() {

        product.setId(2L);
        product.setUser(new User());
        product.setCategory(new Category());

        shoppingCartItem.setId(shoppingCartItemId);
        shoppingCartItem.setQuantity(quantity);
        shoppingCartItem.setTotalCost(totalCost);
        shoppingCartItem.setShoppingCart(new ShoppingCart());
        shoppingCartItem.setProduct(product);
    }

    @Test
    public void ShoppingCartItemToShoppingCartItemTDO() {

        //when
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartItemConverter.ShoppingCartItemToShoppingCartItemDTO(shoppingCartItem);

        assertNotNull(shoppingCartItemDTO);
        assertNotNull(shoppingCartItemDTO.getProductDTO());
        assertNotNull(shoppingCartItemDTO.getShoppingCartDTO());

        assertEquals(shoppingCartItem.getId(), shoppingCartItemDTO.getId());
        assertEquals(shoppingCartItem.getQuantity(), shoppingCartItemDTO.getQuantity());
        assertEquals(shoppingCartItem.getTotalCost(), shoppingCartItemDTO.getTotalCost());
    }

    @Test
    public void ShoppingCartItemDTOToShoppingCartItem() {

        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartItemConverter.ShoppingCartItemToShoppingCartItemDTO(shoppingCartItem);

        //when
        ShoppingCartItem shoppingCartItem = shoppingCartItemConverter.ShoppingCartItemDTOToShoppingCartItem(shoppingCartItemDTO);

        assertNotNull(shoppingCartItem);
        assertNotNull(shoppingCartItem.getProduct());
        assertNotNull(shoppingCartItem.getShoppingCart());

        assertEquals(shoppingCartItem.getId(), shoppingCartItemDTO.getId());
        assertEquals(shoppingCartItem.getQuantity(), shoppingCartItemDTO.getQuantity());
        assertEquals(shoppingCartItem.getTotalCost(), shoppingCartItemDTO.getTotalCost());

    }

}