package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.controllers.UserController;
import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ProductResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ShoppingCartResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.UserResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);

    private final UserResourceAssembler userResourceAssembler;
    private final ProductResourceAssembler productResourceAssembler;
    private final ShoppingCartResourceAssembler shoppingCartResourceAssembler;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserResourceAssembler userResourceAssembler,
             ProductResourceAssembler productResourceAssembler, ShoppingCartResourceAssembler shoppingCartResourceAssembler) {
        this.userRepository = userRepository;
        this.userResourceAssembler = userResourceAssembler;
        this.productResourceAssembler = productResourceAssembler;
        this.shoppingCartResourceAssembler = shoppingCartResourceAssembler;
    }

    @Override
    public Resources<Resource<UserDTO>> getAllUsers() {

        List<Resource<UserDTO>> users = userRepository
                .findAll()
                .stream()
                .map(userConverter::userToUserDTO)
                .map(userResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @Override
    public Resource<UserDTO> getUserById(Long id) {

        UserDTO userDTO =  userRepository
                .findById(id)
                .map(userConverter::userToUserDTO)
                .orElseThrow(NotFoundException::new);

        return userResourceAssembler.toResource(userDTO);
    }

    @Override
    public Resource<UserDTO> saveUser(UserDTO userDTO) {

        User user = userRepository.save(userConverter.userDTOToUser(userDTO));

        return userResourceAssembler.toResource(userConverter.userToUserDTO(user));
    }

    @Override
    public Resource<UserDTO> createNewUser(UserDTO userDTO) {

       return saveUser(userDTO);
    }

    //put request
    @Override
    public Resource<UserDTO> updateUser(Long id, UserDTO userDTO) {

        User user = userConverter.userDTOToUser(userDTO);
        user.setId(id);

        return saveUser(userConverter.userToUserDTO(user));
    }


    @Override
    public Resource<UserDTO> patchUser(Long id, UserDTO userDTO) {

       return userRepository
               .findById(id)
               .map(user -> {

                   if(userDTO.getAddress() != null) {

                       user.setAddress(userDTO.getAddress());
                   }

                   if(userDTO.getCountry() != null) {

                       user.setCountry(userDTO.getCountry());
                   }

                   if(userDTO.getCreationDate() != null) {

                       user.setCreationDate(userDTO.getCreationDate());
                   }

                   if(userDTO.getEmailAddress() != null) {

                       user.setEmailAddress(userDTO.getEmailAddress());
                   }

                   if(userDTO.getFirstName() != null) {

                       user.setFirstName(userDTO.getFirstName());
                   }

                   if(userDTO.getGender() != null) {

                       user.setGender(userDTO.getGender());
                   }

                   if(userDTO.getLastName() != null) {

                       user.setLastName(userDTO.getLastName());
                   }

                   if(userDTO.getPassword() != null) {

                       user.setPassword(userDTO.getPassword());
                   }

                   if(userDTO.getPhoneNumber() != null) {

                       user.setPhoneNumber(userDTO.getPhoneNumber());
                   }

                   if(userDTO.getUsername() != null) {

                       user.setUsername(userDTO.getUsername());
                   }

                   if(userDTO.getProductDTOs() != null) {

                       List<Product> products = new ArrayList<>();

                       for( ProductDTO productDTO : userDTO.getProductDTOs()) {

                           products.add(productConverter.productDTOToProduct(productDTO));
                       }

                       user.setProducts(products);

                   }

                   if(userDTO.getShoppingCartDTO() != null) {

                      user.setShoppingCart(shoppingCartConverter.shoppingCartDTOToShoppingCart(userDTO.getShoppingCartDTO()));
                   }

                   return saveUser(userConverter.userToUserDTO(user));
               })
               .orElseThrow(NotFoundException::new);

    }

    @Override
    public void deleteUserById(Long id) {

        userRepository.deleteById(id);
    }

    @Override
    public Resource<ShoppingCartDTO> createNewShoppingCart(Long id, ShoppingCartDTO shoppingCartDTO) {

        UserDTO userTO = getUserById(id).getContent();

        userTO.setShoppingCartDTO(shoppingCartDTO);   //todo refactor after making it a list
        saveUser(userTO);

        return shoppingCartResourceAssembler.toResource(shoppingCartDTO);
    }

    @Override
    public  Resource<ProductDTO> createNewProduct(Long id, ProductDTO productDTO) {

        UserDTO UserDTO = getUserById(id).getContent();

        UserDTO.getProductDTOs().add(productDTO);
        saveUser(UserDTO);

        return productResourceAssembler.toResource(productDTO);
    }

    @Override
    public Resources<Resource<ProductDTO>> getAllProducts(Long id) {

        List<Resource<ProductDTO>> products = getUserById(id)
                .getContent()
                .getProductDTOs()
                .stream()
                .map(productResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(products,
                linkTo(methodOn(UserController.class).getAllUsersProducts(id)).withSelfRel());

    }

    @Override
    public  Resource<ShoppingCartDTO> getAllShoppingCarts(Long id) {

        ShoppingCartDTO shoppingCart = getUserById(id)
                .getContent()
                .getShoppingCartDTO();

        return shoppingCartResourceAssembler.toResource(shoppingCart);


        //todo update it to pass a list ( after auditing problem). Use same strategy as for products
    }
}
