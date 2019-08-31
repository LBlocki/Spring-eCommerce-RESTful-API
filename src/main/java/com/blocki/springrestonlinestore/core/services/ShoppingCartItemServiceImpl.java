package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartItemMapper;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartItemDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartItemResourceAssembler;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.ShoppingCartItemRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ShoppingCartItemMapper shoppingCartItemConverter = Mappers.getMapper(ShoppingCartItemMapper.class);
    private final ShoppingCartItemResourceAssembler shoppingCartItemResourceAssembler;

    @Autowired
    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository shoppingCartItemRepository,
                                       ShoppingCartItemResourceAssembler shoppingCartItemResourceAssembler) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.shoppingCartItemResourceAssembler = shoppingCartItemResourceAssembler;
    }

    @Override
    public Resource<ShoppingCartItemDTO> getShoppingCartItemById(Long id) {

       return shoppingCartItemRepository
               .findById(id)
               .map(shoppingCartItemConverter::ShoppingCartItemToShoppingCartItemDTO)
               .map(shoppingCartItemResourceAssembler::toResource)
               .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteShoppingCartItemById(Long id) {

        shoppingCartItemRepository.deleteById(id);
    }
}
