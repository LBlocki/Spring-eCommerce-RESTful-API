package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final static Long ID = 2L;
    private final static String firstName = "Michael";
    private final static String userUrl = "/api/v1/users/";

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void getAllUsers() {

        //given
        List<User> users = Arrays.asList(new User(), new User());
        Mockito.when(userRepository.findAll()).thenReturn(users);

        //when
        List<UserDTO> userDTOs = userService.getAllUsers();

        //than
        assertNotNull(userDTOs);
        assertEquals(userDTOs.size(), users.size());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getUserById() {

        //given
        User user = User.builder().id(ID).firstName(firstName).build();
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        //when
        UserDTO userDTO = userService.getUserById(ID);

        //than
        assertNotNull(userDTO);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userUrl + userDTO.getId(), userDTO.getUserUrl());

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());

    }

    @Test
    public void saveUser() {

        //given
        User user = User.builder().id(ID).firstName(firstName).build();
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        //when
        UserDTO savedUserDTO = userService.saveUser(userMapper.userToUserDTO(user));

        //than
        assertNotNull(savedUserDTO);
        assertEquals(savedUserDTO.getId(), user.getId());
        assertEquals(savedUserDTO.getFirstName(), user.getFirstName());
        assertEquals(userUrl + user.getId(), savedUserDTO.getUserUrl());

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void createNewUser() {

        //given
        User user = User.builder().id(ID).firstName(firstName).build();
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        //when
        UserDTO createdUserDTO = userService.createNewUser(userMapper.userToUserDTO(user));

        //than
        assertNotNull(createdUserDTO);
        assertEquals(createdUserDTO.getId(), user.getId());
        assertEquals(createdUserDTO.getFirstName(), user.getFirstName());
        assertEquals(userUrl + user.getId(), createdUserDTO.getUserUrl());

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    }

    @Test
    public void deleteUser() {

        //given
        UserDTO userDTO = userMapper.userToUserDTO(User.builder().id(ID).firstName(firstName).build());

        //when
        userService.deleteUserById(userDTO.getId());

        //than
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());

    }
}