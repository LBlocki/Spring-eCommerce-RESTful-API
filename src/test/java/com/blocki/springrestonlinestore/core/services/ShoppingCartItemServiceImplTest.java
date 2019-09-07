package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.TestEntity;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartItemResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.hateoas.Resource;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartItemServiceImplTest {

    private final TestEntity testEntity = new TestEntity();

    private ShoppingCartItem shoppingCartItem;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @InjectMocks
    private ShoppingCartItemServiceImpl shoppingCartItemService;

    @Spy
    private final ShoppingCartItemResourceAssembler shoppingCartItemResourceAssembler =
            new ShoppingCartItemResourceAssembler();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        shoppingCartItem = testEntity.getShoppingCartItem();
    }

    @Test
    public void getShoppingCartItemById() {

        //given
        Mockito.when(shoppingCartItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCartItem));

        //when
        Resource<ShoppingCartItemDTO> testShoppingCartItemDTO =
                shoppingCartItemService.getShoppingCartItemById(shoppingCartItem.getId());

        //then
        Mockito.verify(shoppingCartItemRepository, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(shoppingCartItemResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(ShoppingCartItemDTO.class));

        Mockito.verifyNoMoreInteractions(shoppingCartItemRepository);
        Mockito.verifyNoMoreInteractions(shoppingCartItemResourceAssembler);

        assertNotNull(testShoppingCartItemDTO);
        assertNotNull(testShoppingCartItemDTO.getContent().getShoppingCartDTO());
        assertNotNull(testShoppingCartItemDTO.getContent().getProductDTO());

        assertEquals(testShoppingCartItemDTO.getContent().getId(), shoppingCartItem.getId());
        assertEquals(testShoppingCartItemDTO.getContent().getShoppingCartDTO().getId(),
                shoppingCartItem.getShoppingCart().getId());
        assertEquals(testShoppingCartItemDTO.getContent().getShoppingCartDTOId(),
                shoppingCartItem.getShoppingCart().getId());
        assertEquals(testShoppingCartItemDTO.getContent().getProductDTO().getId(), shoppingCartItem.getProduct().getId());
        assertEquals(testShoppingCartItemDTO.getContent().getQuantity(), shoppingCartItem.getQuantity());
        assertEquals(testShoppingCartItemDTO.getContent().getTotalCost(), shoppingCartItem.getTotalCost());

    }

    @Test
    public void deleteShoppingCartItemById() {

        //when
       shoppingCartItemService.deleteShoppingCartItemById(shoppingCartItem.getId());

       //then
       Mockito.verify(shoppingCartItemRepository, Mockito.times(1)).deleteById(Mockito.anyLong());

       Mockito.verifyNoMoreInteractions(shoppingCartItemRepository);

    }
}