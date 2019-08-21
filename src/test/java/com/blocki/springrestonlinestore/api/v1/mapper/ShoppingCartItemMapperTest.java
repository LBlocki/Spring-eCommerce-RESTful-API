package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.model.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.model.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartItemMapperTest {

    private ShoppingCartItemMapper shoppingCartItemConverter = ShoppingCartItemMapper.INSTANCE;

    private static final Long shoppingCartItemId = 2L;
    private static final Integer quantity = 10;
    private static final BigDecimal totalCost = BigDecimal.valueOf(30);
    private static final String shoppingCartItemUrl = "/api/v1/shoppingCartItem/2";

    @Test
    public void ShoppingCartItemToShoppingCartItemTDO() {

        ShoppingCartItem shoppingCartItem = ShoppingCartItem.builder()
                .id(shoppingCartItemId)
                .quantity(quantity)
                .totalCost(totalCost)
                .shoppingCart(new ShoppingCart())
                .product(new Product())
                .build();

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

        ShoppingCartItemDTO shoppingCartItemDTO = new ShoppingCartItemDTO();
        shoppingCartItemDTO.setId(shoppingCartItemId);
        shoppingCartItemDTO.setQuantity(quantity);
        shoppingCartItemDTO.setTotalCost(totalCost);
        shoppingCartItemDTO.setShoppingCartDTO(new ShoppingCartDTO());
        shoppingCartItemDTO.setProductDTO(new ProductDTO());
        shoppingCartItemDTO.setShoppingCartItemUrl(shoppingCartItemUrl);

        ShoppingCartItem shoppingCartItem = shoppingCartItemConverter.ShoppingCartItemDTOToShoppingCartItem(shoppingCartItemDTO);

        assertNotNull(shoppingCartItem);
        assertNotNull(shoppingCartItem.getProduct());
        assertNotNull(shoppingCartItem.getShoppingCart());

        assertEquals(shoppingCartItem.getId(), shoppingCartItemDTO.getId());
        assertEquals(shoppingCartItem.getQuantity(), shoppingCartItemDTO.getQuantity());
        assertEquals(shoppingCartItem.getTotalCost(), shoppingCartItemDTO.getTotalCost());

    }

}