package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserListDTO;

public interface UserService {

    UserListDTO getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO saveUser(UserDTO userDTO);

    UserDTO createNewUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    UserDTO patchUser(Long id, UserDTO userDTO);

    void deleteUserById(Long id);

}
