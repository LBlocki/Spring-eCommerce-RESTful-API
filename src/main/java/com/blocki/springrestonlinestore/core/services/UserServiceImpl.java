package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ShoppingCartMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserListDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final ShoppingCartMapper shoppingCartConverter = Mappers.getMapper(ShoppingCartMapper.class);


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserListDTO getAllUsers() {

        List<UserDTO> users = new ArrayList<>();

        for(User user : userRepository.findAll()) {

            UserDTO userDTO = userConverter.userToUserDTO(user);
            users.add(userDTO);
        }

       return new UserListDTO(users);
    }

    @Override
    public UserDTO getUserById(Long id) {

        return userRepository
                .findById(id)
                .map(userConverter::userToUserDTO)
                .orElseThrow(RuntimeException:: new);   //todo implement custom exception
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        User user = userRepository.save(userConverter.userDTOToUser(userDTO));

        return userConverter.userToUserDTO(user);
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {

       return saveUser(userDTO);
    }

    //put request
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User user = userConverter.userDTOToUser(userDTO);
        user.setId(id);

        return saveUser(userConverter.userToUserDTO(user));
    }


    @Override
    public UserDTO patchUser(Long id, UserDTO userDTO) {

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

                       Set<Product> products = new HashSet<>();

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
               .orElseThrow(RuntimeException::new); //todo implement custom exception

    }

    @Override
    public void deleteUserById(Long id) {

        userRepository.deleteById(id);
    }
}
