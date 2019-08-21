package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.User;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.Assert.*;

public class UserMapperTest {


    private UserMapper userConverter = UserMapper.INSTANCE;

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
    private static final String userUrl = "/api/v1/users/5";

    @Test
    public void userToUserDTO() {

        User user = User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .country(country)
                .phoneNumber(phoneNumber)
                .creationDate(creationDate)
                .emailAddress(emailAddress)
                .username(username)
                .password(password)
                .gender(gender)
                .products(new HashSet<>())
                .shoppingCarts(new HashSet<>())
                .build();

        UserDTO userDTO = userConverter.userToUserDTO(user);

        assertNotNull(userDTO);
        assertNotNull(userDTO.getProductDTOs());
        assertNotNull(userDTO.getShoppingCartDTOs());

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
        userDTO.setProductDTOs(new HashSet<>());
        userDTO.setShoppingCartDTOs(new HashSet<>());
        userDTO.setUserUrl(userUrl);

        User user = userConverter.userDTOToUser(userDTO);

        assertNotNull(user);
        assertNotNull(user.getProducts());
        assertNotNull(user.getShoppingCarts());

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