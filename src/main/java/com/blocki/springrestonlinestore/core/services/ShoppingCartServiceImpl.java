package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartItemMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
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
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);
    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ShoppingCartItemMapper shoppingCartItemConverter = Mappers.getMapper(ShoppingCartItemMapper.class);
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

        shoppingCartRepository.deleteById(id);

    }

    @Override
    public Resource<ShoppingCartDTO> createPurchaseRequest(Long id) {

        ShoppingCartDTO shoppingCartDTO = getShoppingCartById(id).getContent();
        shoppingCartDTO.setCartStatus(ShoppingCart.CartStatus.COMPLETED);

        //todo now add to history here or with auditing

        return shoppingCartResourceAssembler.toResource(shoppingCartDTO);
    }

    @Override
    public Resource<ShoppingCartDTO> createCancellationRequest(Long id) {

        ShoppingCartDTO shoppingCartDTO = getShoppingCartById(id).getContent();
        shoppingCartDTO.setCartStatus(ShoppingCart.CartStatus.COMPLETED);

        //todo now add to history here or with auditing

        return shoppingCartResourceAssembler.toResource(shoppingCartDTO);
    }

    @Override
    public Resource<ShoppingCartItemDTO> createNewShoppingCartItem(Long id, ShoppingCartItemDTO shoppingCartItemDTO) {

        ShoppingCartDTO shoppingCartDTO = getShoppingCartById(id).getContent();

        shoppingCartItemDTO.setShoppingCartDTO(shoppingCartDTO);
        shoppingCartDTO.getShoppingCartItemDTOs().add(shoppingCartItemDTO);

        saveShoppingCart(shoppingCartDTO);

        return shoppingCartItemResourceAssembler.toResource(shoppingCartItemDTO);
    }
}
