package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.TestEntity;
import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartItemMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartItemResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartServiceImplTest {

    private final TestEntity testEntity = new TestEntity();

    private ShoppingCart shoppingCart;

    private ShoppingCartItemMapper shoppingCartItemConverter = Mappers.getMapper(ShoppingCartItemMapper.class);

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Spy
    private ShoppingCartResourceAssembler shoppingCartResourceAssembler = new ShoppingCartResourceAssembler();

    @Spy
    private ShoppingCartItemResourceAssembler shoppingCartItemResourceAssembler =
            new ShoppingCartItemResourceAssembler();


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        shoppingCart = testEntity.getShoppingCart();
    }

    @Test
    public void getShoppingCartById() {

        //given
        Mockito.when(shoppingCartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCart));

        //when
        Resource<ShoppingCartDTO> testShoppingCartDTO = shoppingCartService.getShoppingCartById(shoppingCart.getId());

        //then
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(shoppingCartResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(ShoppingCartDTO.class));

        Mockito.verifyNoMoreInteractions(shoppingCartRepository);
        Mockito.verifyNoMoreInteractions(shoppingCartResourceAssembler);

        assertNotNull(testShoppingCartDTO);
        assertNotNull(testShoppingCartDTO.getContent().getUserDTO());
        assertNotNull(testShoppingCartDTO.getContent().getShoppingCartItemDTOs());

        assertEquals(testShoppingCartDTO.getContent().getUserDTO().getId(), shoppingCart.getUser().getId());
        assertEquals(testShoppingCartDTO.getContent().getId(), shoppingCart.getId());
        assertEquals(testShoppingCartDTO.getContent().getUserDTOId(), shoppingCart.getUser().getId());
        assertEquals(testShoppingCartDTO.getContent().getShoppingCartItemDTOs().size(), shoppingCart
                .getShoppingCartItems().size());
        assertEquals(testShoppingCartDTO.getContent().getCartStatus(), shoppingCart.getCartStatus());
        assertEquals(testShoppingCartDTO.getContent().getCreationDate(), shoppingCart.getCreationDate());
    }

    @Test
    public void deleteShoppingCartById() {

        //when
        shoppingCartService.deleteShoppingCartById(shoppingCart.getId());

        //then
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).deleteById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(shoppingCartRepository);
    }

    @Test
    public void createPurchaseRequest() {

        //given
        Mockito.when(shoppingCartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCart));

        //when
        Resource<ShoppingCartDTO> testShoppingCartDTO = shoppingCartService.createPurchaseRequest(shoppingCart.getId());

        //then
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(shoppingCartResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(ShoppingCartDTO.class));

        Mockito.verifyNoMoreInteractions(shoppingCartRepository);
        Mockito.verifyNoMoreInteractions(shoppingCartResourceAssembler);

        assertNotNull(testShoppingCartDTO);
        assertEquals(testShoppingCartDTO.getContent().getCartStatus(), ShoppingCart.CartStatus.COMPLETED);
    }

    @Test
    public void createCancellationRequest() {

        //given
        Mockito.when(shoppingCartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCart));

        //when
        shoppingCartService.createCancellationRequest(shoppingCart.getId());

        //then
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).deleteById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(shoppingCartRepository);
    }

    @Test
    public void createNewShoppingCartItem() {

        //given
        Mockito.when(shoppingCartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCart));
        Mockito.when(shoppingCartRepository.save(Mockito.any(ShoppingCart.class))).thenReturn(shoppingCart);
        ShoppingCartItem shoppingCartItem = shoppingCart.getShoppingCartItems().get(0);

        //when
        Resource<ShoppingCartItemDTO> testCreatedShoppingCartItemDTO = shoppingCartService
                .createNewShoppingCartItem(shoppingCart.getId(), shoppingCartItemConverter
                        .ShoppingCartItemToShoppingCartItemDTO(shoppingCartItem));

        //then
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(shoppingCartRepository, Mockito.times(1))
                .save(Mockito.any(ShoppingCart.class));
        Mockito.verify(shoppingCartResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(ShoppingCartDTO.class));
        Mockito.verify(shoppingCartItemResourceAssembler, Mockito.times(1))
                .toResource(Mockito.any(ShoppingCartItemDTO.class));

        Mockito.verifyNoMoreInteractions(shoppingCartRepository);
        Mockito.verifyNoMoreInteractions(shoppingCartItemResourceAssembler);
        Mockito.verifyNoMoreInteractions(shoppingCartItemResourceAssembler);

        assertNotNull(testCreatedShoppingCartItemDTO);
        assertNotNull(testCreatedShoppingCartItemDTO.getContent().getProductDTO());
        assertNotNull(testCreatedShoppingCartItemDTO.getContent().getShoppingCartDTO());

        assertEquals(testCreatedShoppingCartItemDTO.getContent()
                .getShoppingCartDTOId(), shoppingCartItem.getShoppingCart().getId());
        assertEquals(testCreatedShoppingCartItemDTO.getContent().getId(), shoppingCartItem.getId());
        assertEquals(testCreatedShoppingCartItemDTO.getContent()
                .getProductDTO().getId(), shoppingCartItem.getProduct().getId());
        assertEquals(testCreatedShoppingCartItemDTO.getContent().getTotalCost(), shoppingCartItem.getTotalCost());
        assertEquals(testCreatedShoppingCartItemDTO.getContent().getQuantity(), shoppingCartItem.getQuantity());
    }

    @Test
    public void getAllShoppingCartItems() {

        //given
        Mockito.when(shoppingCartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(shoppingCart));
        List<ShoppingCartItem> shoppingCartItemList = shoppingCart.getShoppingCartItems();


        //when
        Resources<Resource<ShoppingCartItemDTO>> testShoppingCartItemList =
                shoppingCartService.getAllShoppingCartItems(shoppingCart.getId());

        //then
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(shoppingCartRepository);

        assertNotNull(testShoppingCartItemList);

        assertEquals(testShoppingCartItemList.getContent().size(),shoppingCartItemList.size());
    }
}