package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.ShoppingCartDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserMapperTest {


    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);

    private static final Long userId = 2L;
    private static final String firstName = "Michael";
    private static final String lastName = "Borrows";
    private static final String address = "221B Baker Street";
    private static final String country = "Great Britain";
    private static final String phoneNumber = "213122112";
    private static final LocalDate creationDate = LocalDate.of(2000,12,12);
    private static final String emailAddress = "dsadsa@asdsa.sda";
    private static final String username = "UserMyName";
    private static final char[] password = {'q','s','t'};
    private static final User.Gender gender = User.Gender.MALE;

    @Test
    public void userToUserDTO() {



        User user = new User();
        user.setId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setCountry(country);
        user.setPhoneNumber(phoneNumber);
        user.setCreationDate(creationDate);
        user.setEmailAddress(emailAddress);
        user.setUsername(username);
        user.setPassword(password);
        user.setGender(gender);
        user.setProducts(new ArrayList<>());
        user.setShoppingCart(new ShoppingCart());

        UserDTO userDTO = userConverter.userToUserDTO(user);

        assertNotNull(userDTO);
        assertNotNull(userDTO.getProductDTOs());
        assertNotNull(userDTO.getShoppingCartDTO());

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getPhoneNumber(), userDTO.getPhoneNumber());
        assertEquals(user.getCountry(), userDTO.getCountry());
        assertEquals(user.getCreationDate(), userDTO.getCreationDate());
        assertEquals(user.getEmailAddress(), userDTO.getEmailAddress());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertArrayEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getGender(), userDTO.getGender());
    }

    @Test
    public void userDTOToUser() {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setAddress(address);
        userDTO.setCountry(country);
        userDTO.setPhoneNumber(phoneNumber);
        userDTO.setCreationDate(creationDate);
        userDTO.setEmailAddress(emailAddress);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setGender(gender);
        userDTO.setProductDTOs(new ArrayList<>());
        userDTO.setShoppingCartDTO(new ShoppingCartDTO());

        User user = userConverter.userDTOToUser(userDTO);

        assertNotNull(user);
        assertNotNull(user.getProducts());
        assertNotNull(user.getShoppingCart());

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getPhoneNumber(), userDTO.getPhoneNumber());
        assertEquals(user.getCountry(), userDTO.getCountry());
        assertEquals(user.getCreationDate(), userDTO.getCreationDate());
        assertEquals(user.getEmailAddress(), userDTO.getEmailAddress());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertArrayEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getGender(), userDTO.getGender());

    }

}