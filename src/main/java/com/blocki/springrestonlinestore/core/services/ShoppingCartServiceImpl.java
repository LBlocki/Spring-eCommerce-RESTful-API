package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.controllers.ShoppingCartController;
import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartItemMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartItemResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);
    private final ShoppingCartItemMapper shoppingCartItemConverter =
            Mappers.getMapper(ShoppingCartItemMapper.class);

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartResourceAssembler shoppingCartResourceAssembler;
    private final ShoppingCartItemResourceAssembler shoppingCartItemResourceAssembler;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   ShoppingCartResourceAssembler shoppingCartResourceAssembler,
                                   ShoppingCartItemResourceAssembler shoppingCartItemResourceAssembler) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartResourceAssembler = shoppingCartResourceAssembler;
        this.shoppingCartItemResourceAssembler = shoppingCartItemResourceAssembler;
    }

    @Override
    public Resource<ShoppingCartDTO> getShoppingCartById(Long id) {

        return shoppingCartRepository
                .findById(id)
                .map(shoppingCartConverter::shoppingCartToShoppingCartDTO)
                .map(shoppingCartResourceAssembler::toResource)
                .orElseThrow(NotFoundException::new);
    }


    private void saveShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = shoppingCartRepository.save(shoppingCartConverter
                .shoppingCartDTOToShoppingCart(shoppingCartDTO));

        shoppingCartResourceAssembler.toResource(shoppingCartConverter
                .shoppingCartToShoppingCartDTO(shoppingCart));
    }

    @Override
    public void deleteShoppingCartById(Long id) {

        getShoppingCartById(id).getContent().getUserDTO().setShoppingCartDTO(null);
        shoppingCartRepository.deleteById(id);

    }

    @Override
    public Resource<ShoppingCartDTO> createPurchaseRequest(Long id) {

        ShoppingCartDTO shoppingCartDTO = getShoppingCartById(id).getContent();
        shoppingCartDTO.setCartStatus(ShoppingCart.CartStatus.COMPLETED);
        //here it would go to the orders history

        return shoppingCartResourceAssembler.toResource(shoppingCartDTO);
    }

    @Override
    public void createCancellationRequest(Long id) {

        ShoppingCartDTO shoppingCartDTO = getShoppingCartById(id).getContent();

        deleteShoppingCartById(shoppingCartDTO.getId());

    }

    @Override
    public Resource<ShoppingCartItemDTO> createNewShoppingCartItem(Long id, ShoppingCartItemDTO shoppingCartItemDTO) {

        ShoppingCartDTO shoppingCartDTO = getShoppingCartById(id).getContent();

        shoppingCartItemDTO.setShoppingCartDTO(shoppingCartDTO);
        shoppingCartDTO.getShoppingCartItemDTOs().add(shoppingCartItemDTO);

        saveShoppingCart(shoppingCartDTO);

        return shoppingCartItemResourceAssembler.toResource(shoppingCartItemDTO);
    }

    @Override
    public Resources<Resource<ShoppingCartItemDTO>> getAllShoppingCartItems(Long id) {

        List<Resource<ShoppingCartItemDTO>> shoppingCartItemList =  getShoppingCartById(id)
                .getContent()
                .getShoppingCartItemDTOs()
                .stream()
                .map(shoppingCartItemResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(shoppingCartItemList,
                linkTo(methodOn(ShoppingCartController.class).getAllShoppingCartItems(id)).withSelfRel());
    }
}
