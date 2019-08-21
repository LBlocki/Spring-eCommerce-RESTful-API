package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserListDTO;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private static final Long ID = 2L;
    private static final String firstName = "Michael";

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

    }

    @Test
    public void getListOfUsers() throws  Exception {

        //given
        UserListDTO userDTOs = new UserListDTO( Arrays.asList(new UserDTO(), new UserDTO()));

        Mockito.when(userService.getAllUsers()).thenReturn(userDTOs);

        //than
        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users", Matchers.hasSize(2)));

        Mockito.verify(userService, Mockito.times(1)).getAllUsers();

    }

    @Test
    public void getUserById() throws Exception {

        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setUserUrl(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID);
        userDTO.setId(ID);

        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(userDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders.get(UserController.USER_CONTROLLER_BASIC_URL + "/" + userDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.equalTo(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_url", Matchers.equalTo(userDTO.getUserUrl())));

        Mockito.verify(userService, Mockito.times(1)).getUserById(Mockito.anyLong());
    }

    @Test
    public void createNewUser() throws Exception {

        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setId(ID);
        userDTO.setLastName("sda");
        userDTO.setEmailAddress("dsa@wew.cs");
        userDTO.setPassword(new char[]{'s','s','s','s','s','s','s'});
        userDTO.setGender(User.Gender.MALE);
        userDTO.setCreationDate(LocalDate.MAX);
        userDTO.setAddress("dsa");
        userDTO.setPhoneNumber("2312312312");
        userDTO.setCountry("dsa");
        userDTO.setUsername("sda");
        userDTO.setUserUrl(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID);

        Mockito.when(userService.createNewUser(Mockito.any(UserDTO.class))).thenReturn(userDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        //than
        mockMvc.perform(MockMvcRequestBuilders.post(UserController.USER_CONTROLLER_BASIC_URL )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.equalTo(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_url", Matchers.equalTo(userDTO.getUserUrl())));

        Mockito.verify(userService, Mockito.times(1)).createNewUser(Mockito.any(UserDTO.class));
    }

    @Test
    public void updateUser() throws Exception{

        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setId(ID);
        userDTO.setLastName("sda");
        userDTO.setEmailAddress("dsa@wew.cs");
        userDTO.setPassword(new char[]{'s','s','s','s','s','s','s'});
        userDTO.setGender(User.Gender.MALE);
        userDTO.setCreationDate(LocalDate.MAX);
        userDTO.setAddress("dsa");
        userDTO.setPhoneNumber("2312312312");
        userDTO.setCountry("dsa");
        userDTO.setUsername("sda");
        userDTO.setUserUrl(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID);

        Mockito.when(userService.updateUser(Mockito.anyLong(), Mockito.any(UserDTO.class))).thenReturn(userDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        //than
        mockMvc.perform(MockMvcRequestBuilders.put(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.equalTo(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_url", Matchers.equalTo(userDTO.getUserUrl())));

        Mockito.verify(userService, Mockito.times(1)).updateUser(Mockito.anyLong(), Mockito.any(UserDTO.class));

    }

    @Test
    public void patchUser() throws Exception{

        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setId(ID);
        userDTO.setLastName("sda");
        userDTO.setEmailAddress("dsa@wew.cs");
        userDTO.setPassword(new char[]{'s','s','s','s','s','s','s'});
        userDTO.setGender(User.Gender.MALE);
        userDTO.setCreationDate(LocalDate.MAX);
        userDTO.setAddress("dsa");
        userDTO.setPhoneNumber("2312312312");
        userDTO.setCountry("dsa");
        userDTO.setUsername("sda");
        userDTO.setUserUrl(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID);

        Mockito.when(userService.patchUser(Mockito.anyLong(), Mockito.any(UserDTO.class))).thenReturn(userDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        //than
        mockMvc.perform(MockMvcRequestBuilders.patch(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(userDTO.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.equalTo(userDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user_url", Matchers.equalTo(userDTO.getUserUrl())));

        Mockito.verify(userService, Mockito.times(1)).patchUser(Mockito.anyLong(), Mockito.any(UserDTO.class));
    }

    @Test
    public void deleteUserById() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete(UserController.USER_CONTROLLER_BASIC_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).deleteUserById(Mockito.any());
    }
}