package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserListDTO;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private static final String USER_BASIC_URL = "/api/v1/users/";  //todo use controller URL

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserListDTO getAllUsers() {

        List<UserDTO> users = new ArrayList<>();

        for(User user : userRepository.findAll()) {

            UserDTO userDTO = userConverter.userToUserDTO(user);
            userDTO.setUserUrl(getNewUserUrl(user.getId()));
            users.add(userDTO);
        }

       return new UserListDTO(users);
    }

    @Override
    public UserDTO getUserById(Long id) {

        return userRepository
                .findById(id)
                .map(user -> {
                    UserDTO  userDTO = userConverter.userToUserDTO(user);
                    userDTO.setUserUrl(getNewUserUrl(user.getId()));
                    return userDTO;
                })
                .orElseThrow(RuntimeException:: new);   //todo implement custom exception
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        User user = userRepository.save(userConverter.userDTOToUser(userDTO));

        UserDTO savedUserDTO = userConverter.userToUserDTO(user);

        savedUserDTO.setUserUrl(getNewUserUrl(savedUserDTO.getId()));

        return savedUserDTO;
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

                       //todo use product service to patch products from user
                   }

                   if(userDTO.getShoppingCartDTO() != null) {

                       //todo use shopping cart service to patch shopping carts from user
                   }

                   UserDTO savedUser = saveUser(userConverter.userToUserDTO(user));
                   savedUser.setUserUrl(getNewUserUrl(user.getId()));

                   return savedUser;
               })
               .orElseThrow(RuntimeException::new); //todo implement custom exception

    }

    @Override
    public void deleteUserById(Long id) {

        userRepository.deleteById(id);
    }

    private String getNewUserUrl(Long id) {

        return USER_BASIC_URL + id;
    }
}
