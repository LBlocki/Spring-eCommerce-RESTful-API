package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "products", target = "productDTOs")
    @Mapping(source = "shoppingCart", target = "shoppingCartDTO")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "productDTOs", target = "products")
    @Mapping(source = "shoppingCartDTO", target = "shoppingCart")
    User userDTOToUser(UserDTO userDTO);
}
