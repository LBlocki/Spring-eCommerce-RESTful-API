package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.bootstrap.TestEntityGenerator;
import com.blocki.springrestonlinestore.core.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.*;

public class UserMapperTest {


    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    private User user;

    @Before
    public void setUp() {

        user = testEntityGenerator.generateUser();
    }

    @Test
    public void userToUserDTO() {

        //when
        UserDTO userDTO = userConverter.userToUserDTO(user);

        //then
        assertNotNull(userDTO);
        assertNotNull(userDTO.getProductDTOs());
        assertNotNull(userDTO.getOrderDTO());
        assertNotNull(userDTO.getOrderDTO().getOrderItemDTOS());

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getAddress(), user.getAddress());
        assertEquals(userDTO.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userDTO.getCountry(), user.getCountry());
        assertEquals(userDTO.getCreationDate(), user.getCreationDate());
        assertEquals(userDTO.getEmailAddress(), user.getEmailAddress());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getGender(), user.getGender());
        assertEquals(userDTO.getProductDTOs().size(), user.getProducts().size());
        assertEquals(userDTO.getOrderDTO().getId(), user.getOrder().getId());

        assertArrayEquals(userDTO.getPassword(), user.getPassword());
    }

    @Test
    public void userDTOToUser() {

        //given
        UserDTO userDTO = userConverter.userToUserDTO(user);

        //when
        User TestUser = userConverter.userDTOToUser(userDTO);

        //then
        assertNotNull(TestUser);
        assertNotNull(TestUser.getProducts());
        assertNotNull(TestUser.getOrder());

        assertEquals(TestUser.getId(), userDTO.getId());
        assertEquals(TestUser.getFirstName(), userDTO.getFirstName());
        assertEquals(TestUser.getLastName(), userDTO.getLastName());
        assertEquals(TestUser.getAddress(), userDTO.getAddress());
        assertEquals(TestUser.getPhoneNumber(), userDTO.getPhoneNumber());
        assertEquals(TestUser.getCountry(), userDTO.getCountry());
        assertEquals(TestUser.getCreationDate(), userDTO.getCreationDate());
        assertEquals(TestUser.getEmailAddress(), userDTO.getEmailAddress());
        assertEquals(TestUser.getUsername(), userDTO.getUsername());
        assertEquals(TestUser.getGender(), userDTO.getGender());

        assert userDTO.getProductDTOs() != null;
        assertEquals(TestUser.getProducts().size(), userDTO.getProductDTOs().size());

        assert userDTO.getOrderDTO() != null;
        assertEquals(TestUser.getOrder().getId(), userDTO.getOrderDTO().getId());

        assertArrayEquals(TestUser.getPassword(), userDTO.getPassword());
    }
}