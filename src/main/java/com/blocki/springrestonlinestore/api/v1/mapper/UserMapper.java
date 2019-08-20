package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.UserDTO;
import com.blocki.springrestonlinestore.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
