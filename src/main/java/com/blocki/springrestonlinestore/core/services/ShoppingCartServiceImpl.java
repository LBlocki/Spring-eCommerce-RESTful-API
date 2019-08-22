package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartListDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
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
    private final UserService userService;

    private static final String SHOPPING_CART_BASIC_URL = "/api/v1/users/{id}/shoppingCarts";

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
    }

    @Override
    public ShoppingCartListDTO getAllShoppingCarts() {

        List<ShoppingCartDTO> shoppingCartDTOs = new ArrayList<>();

        for(ShoppingCart shoppingCart : shoppingCartRepository.findAll()) {

            ShoppingCartDTO shoppingCartDTO = shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart);
            shoppingCartDTO.setShoppingCartUrl(getNewShoppingCartUrl(shoppingCartDTO.getId()));

            shoppingCartDTOs.add(shoppingCartDTO);
        }

        return new ShoppingCartListDTO(shoppingCartDTOs);
    }

    @Override
    public ShoppingCartDTO getShoppingCartById(Long id) {

        return shoppingCartRepository
                .findById(id)
                .map(shoppingCart -> {
                    ShoppingCartDTO  shoppingCartDTO = shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart);
                    shoppingCartDTO.setShoppingCartUrl(getNewShoppingCartUrl(shoppingCart.getId()));
                    return shoppingCartDTO;
                })
                .orElseThrow(RuntimeException:: new);   //todo implement custom exception
    }

    @Override
    public ShoppingCartDTO saveShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = shoppingCartRepository.save(shoppingCartConverter.shoppingCartDTOToShoppingCart(shoppingCartDTO));

        ShoppingCartDTO savedShoppingCartDTO = shoppingCartConverter.shoppingCartToShoppingCartDTO(shoppingCart);

        savedShoppingCartDTO.setShoppingCartUrl(getNewShoppingCartUrl(savedShoppingCartDTO.getId()));

        return savedShoppingCartDTO;
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

                        //todo implement using service after implementing shoppingCartItemService
                    }

                    if(shoppingCartDTO.getUserDTO() != null) {

                        userService.patchUser(id, shoppingCartDTO.getUserDTO());
                    }

                    ShoppingCartDTO savedShoppingCartDTO = shoppingCartConverter
                            .shoppingCartToShoppingCartDTO(shoppingCartRepository.save(shoppingCart));

                    savedShoppingCartDTO.setShoppingCartUrl(getNewShoppingCartUrl(savedShoppingCartDTO.getId()));

                    return savedShoppingCartDTO;
                }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteShoppingCartById(Long id) {

        shoppingCartRepository.deleteById(id);

    }

    private String getNewShoppingCartUrl(Long id) {

        return SHOPPING_CART_BASIC_URL + "/" + id;
    }
}
