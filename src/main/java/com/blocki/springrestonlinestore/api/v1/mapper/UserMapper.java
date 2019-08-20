package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.UserDTO;
import com.blocki.springrestonlinestore.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "products", target = "productDTOs")
    @Mapping(source = "shoppingCarts", target = "shoppingCartDTOs")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "productDTOs", target = "products")
    @Mapping(source = "shoppingCartDTOs", target = "shoppingCarts")
    User userDTOToUser(UserDTO userDTO);
}
