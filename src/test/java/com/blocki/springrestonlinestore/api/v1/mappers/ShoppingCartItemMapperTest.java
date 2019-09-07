package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.TestEntity;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartItemMapperTest {

    private final ShoppingCartItemMapper shoppingCartItemConverter = Mappers.getMapper(ShoppingCartItemMapper.class);
    private final TestEntity testEntity = new TestEntity();

    private ShoppingCartItem shoppingCartItem;

    @Before
    public void setUp() {

       shoppingCartItem = testEntity.getShoppingCartItem();
    }

    @Test
    public void ShoppingCartItemToShoppingCartItemTDO() {

        //when
        ShoppingCartItemDTO testShoppingCartItemDTO = shoppingCartItemConverter.ShoppingCartItemToShoppingCartItemDTO(shoppingCartItem);

        //then
        assertNotNull(testShoppingCartItemDTO);
        assertNotNull(testShoppingCartItemDTO.getProductDTO());
        assertNotNull(testShoppingCartItemDTO.getShoppingCartDTO());

        assertEquals(testShoppingCartItemDTO.getId(), shoppingCartItem.getId());
        assertEquals(testShoppingCartItemDTO.getQuantity(), shoppingCartItem.getQuantity());
        assertEquals(testShoppingCartItemDTO.getTotalCost(), shoppingCartItem.getTotalCost());
        assertEquals(testShoppingCartItemDTO.getProductDTO().getId(), shoppingCartItem.getProduct().getId());
        assertEquals(testShoppingCartItemDTO.getShoppingCartDTO().getId(), shoppingCartItem.getShoppingCart().getId());
        assertEquals(testShoppingCartItemDTO.getShoppingCartDTOId(), shoppingCartItem.getShoppingCart().getId());
    }

    @Test
    public void ShoppingCartItemDTOToShoppingCartItem() {

        //given
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartItemConverter.ShoppingCartItemToShoppingCartItemDTO(shoppingCartItem);

        //when
        ShoppingCartItem testShoppingCartItem = shoppingCartItemConverter.ShoppingCartItemDTOToShoppingCartItem(shoppingCartItemDTO);

        //then
        assertNotNull(testShoppingCartItem);
        assertNotNull(testShoppingCartItem.getProduct());
        assertNotNull(testShoppingCartItem.getShoppingCart());

        assertEquals(testShoppingCartItem.getId(), shoppingCartItemDTO.getId());
        assertEquals(testShoppingCartItem.getQuantity(), shoppingCartItemDTO.getQuantity());
        assertEquals(testShoppingCartItem.getTotalCost(), shoppingCartItemDTO.getTotalCost());
        assertEquals(testShoppingCartItem.getProduct().getId(), shoppingCartItemDTO.getId());
        assertEquals(testShoppingCartItem.getShoppingCart().getId(), shoppingCartItemDTO.getShoppingCartDTO().getId());
    }
}