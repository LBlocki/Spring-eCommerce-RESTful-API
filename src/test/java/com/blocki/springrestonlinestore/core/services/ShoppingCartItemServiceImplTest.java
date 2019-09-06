package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartItemResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.*;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.hateoas.Resource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartItemServiceImplTest {

    private static final Long shoppingCartItemId = 2L;
    private static final Integer quantity = 10;
    private static final BigDecimal totalCost = BigDecimal.valueOf(30);

    private Product product = new Product();
    private ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
    private ShoppingCart shoppingCart = new ShoppingCart();
    private User user = new User();

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

        product.setId(2L);
        product.setCategory(new Category());

        shoppingCartItem.setId(shoppingCartItemId);
        shoppingCartItem.setQuantity(quantity);
        shoppingCartItem.setTotalCost(totalCost);

        user.setId(1L);
        user.setShoppingCart(shoppingCart);
        user.getProducts().add(product);
        product.setUser(user);

        shoppingCartItem.setProduct(product);
        shoppingCart.setUser(user);

        shoppingCart.setId(2L);
        shoppingCart.getShoppingCartItems().add(shoppingCartItem);
        shoppingCartItem.setShoppingCart(shoppingCart);


    }

    @Test
    public void getShoppingCartItemById() {

        //given
        Mockito.when(shoppingCartItemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(shoppingCartItem));

        //when
        Resource<ShoppingCartItemDTO> shoppingCartItemDTO =
                shoppingCartItemService.getShoppingCartItemById(shoppingCartItemId);

        //than
        Mockito.verify(shoppingCartItemRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(shoppingCartItemResourceAssembler, Mockito.times(1)).toResource(Mockito.any(ShoppingCartItemDTO.class));
        Mockito.verifyNoMoreInteractions(shoppingCartItemRepository);
        Mockito.verifyNoMoreInteractions(shoppingCartItemResourceAssembler);

        assertNotNull(shoppingCartItemDTO);
        assertNotNull(shoppingCartItemDTO.getContent().getShoppingCartDTO());
        assertNotNull(shoppingCartItemDTO.getContent().getProductDTO());

        assertEquals(shoppingCartItemDTO.getContent().getId(), shoppingCartItem.getId());
    }

    @Test
    public void deleteShoppingCartItemById() {

       shoppingCartItemService.deleteShoppingCartItemById(shoppingCartItemId);

       Mockito.verify(shoppingCartItemRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
       Mockito.verifyNoMoreInteractions(shoppingCartItemRepository);

    }
}