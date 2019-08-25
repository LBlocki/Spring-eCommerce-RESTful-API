package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartItemMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartListDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);
    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ShoppingCartItemMapper shoppingCartItemConverter = Mappers.getMapper(ShoppingCartItemMapper.class);

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCartListDTO getAllShoppingCarts() {

        List<ShoppingCartDTO> shoppingCartDTOs = new ArrayList<>();

        for(ShoppingCart shoppingCart : shoppingCartRepository.findAll()) {

            ShoppingCartDTO shoppingCartDTO = shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart);
            shoppingCartDTOs.add(shoppingCartDTO);
        }

        return new ShoppingCartListDTO(shoppingCartDTOs);
    }

    @Override
    public ShoppingCartDTO getShoppingCartById(Long id) {

        return shoppingCartRepository
                .findById(id)
                .map(shoppingCartConverter::shoppingCartToShoppingCartDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public ShoppingCartDTO saveShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = shoppingCartRepository.save(shoppingCartConverter.shoppingCartDTOToShoppingCart(shoppingCartDTO));


        return shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart);
    }

    @Override
    public ShoppingCartDTO createNewShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        return saveShoppingCart(shoppingCartDTO);
    }

    @Override
    public ShoppingCartDTO updateShoppingCart(Long id, ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = shoppingCartConverter.shoppingCartDTOToShoppingCart(shoppingCartDTO);
        shoppingCart.setId(id);

        return saveShoppingCart(shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart));
    }

    @Override
    public ShoppingCartDTO patchShoppingCart(Long id, ShoppingCartDTO shoppingCartDTO) {

        return shoppingCartRepository
                .findById(id)
                .map(shoppingCart -> {

                    if(shoppingCartDTO.getCartStatus() != null) {

                        shoppingCart.setCartStatus(shoppingCartDTO.getCartStatus());
                    }

                    if(shoppingCartDTO.getCreationDate() != null) {

                        shoppingCart.setCreationDate(shoppingCart.getCreationDate());
                    }

                    if(shoppingCartDTO.getShoppingCartItemDTOs() != null) {

                        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

                        for( ShoppingCartItemDTO shoppingCartItemDTO : shoppingCartDTO.getShoppingCartItemDTOs()) {

                            shoppingCartItems.add(shoppingCartItemConverter.ShoppingCartItemDTOToShoppingCartItem(shoppingCartItemDTO));
                        }

                        shoppingCart.setShoppingCartItems(shoppingCartItems);
                    }

                    if(shoppingCartDTO.getUserDTO() != null) {

                        shoppingCart.setUser(userConverter.userDTOToUser(shoppingCartDTO.getUserDTO()));
                    }

                    return shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCartRepository.save(shoppingCart));

                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteShoppingCartById(Long id) {

        shoppingCartRepository.deleteById(id);

    }
}
