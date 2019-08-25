package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartItemMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemListDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCartItem;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartItemRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ShoppingCartItemMapper shoppingCartItemConverter = Mappers.getMapper(ShoppingCartItemMapper.class);

    @Autowired
    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Override
    public ShoppingCartItemListDTO getAllShoppingCartItems() {

        List<ShoppingCartItemDTO> categoryDTOs = new ArrayList<>();

        for(ShoppingCartItem category : shoppingCartItemRepository.findAll()) {

            ShoppingCartItemDTO newCategoryDTO = shoppingCartItemConverter.ShoppingCartItemToShoppingCartItemDTO(category);
            categoryDTOs.add(newCategoryDTO);
        }

        return new ShoppingCartItemListDTO(categoryDTOs);
    }

    @Override
    public ShoppingCartItemDTO getShoppingCartItemById(Long id) {

       return shoppingCartItemRepository
               .findById(id)
               .map(shoppingCartItemConverter::ShoppingCartItemToShoppingCartItemDTO)
               .orElseThrow(NotFoundException::new);
    }

    @Override
    public ShoppingCartItemDTO saveShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO) {

       ShoppingCartItem shoppingCartItem = shoppingCartItemConverter
               .ShoppingCartItemDTOToShoppingCartItem(shoppingCartItemDTO);

       return shoppingCartItemConverter
               .ShoppingCartItemToShoppingCartItemDTO(shoppingCartItemRepository.save(shoppingCartItem));
    }

    @Override
    public ShoppingCartItemDTO createNewShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO) {

       return  saveShoppingCartItem(shoppingCartItemDTO);
    }

    @Override
    public void deleteShoppingCartItemById(Long id) {

        shoppingCartItemRepository.deleteById(id);
    }
}
