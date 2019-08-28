package com.blocki.springrestonlinestore.core.bootstrap;

import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import com.blocki.springrestonlinestore.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    private static final Long ID = 2L;
    private static final String FIRST_NAME = "Sherlock";
    private static final String LAST_NAME = "Holmes";
    private static final String EMAIL_ADDRESS = "emailAdress@gmail.com";
    private static final char[] PASSWORD = {'s','s','s','s','s','s','s'};
    private static final User.Gender GENDER = User.Gender.MALE;
    private static final LocalDate CREATION_DATE = LocalDate.now();
    private static final String ADDRESS = "221B Baker Street";
    private static final String PHONE_NUMBER = "123456789";
    private static final String COUNTRY = "Poland";
    private static final String USERNAME = "GreatUser";

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;

        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {



       Category clothes = Category.builder().id(1L).name("Clothes").build();
       Category food = Category.builder().id(2L).name("Food").build();

       categoryRepository.save(clothes);
       categoryRepository.save(food);

       loadUsers();


    }

    private void loadUsers() {

        User firstUser = new User();
        firstUser.setId(1L);
        fillUser(firstUser);

        User secondUser = new User();
        secondUser.setId(2L);
        fillUser(secondUser);

        secondUser.setEmailAddress(EMAIL_ADDRESS + "f");
        secondUser.setUsername(USERNAME + "f");

        userService.createNewUser(UserMapper.INSTANCE.userToUserDTO(firstUser));
        userService.createNewUser(UserMapper.INSTANCE.userToUserDTO(secondUser));

    }

    private void fillUser(User userTobeFilled) {

        userTobeFilled.setFirstName(FIRST_NAME);
        userTobeFilled.setLastName(LAST_NAME);
        userTobeFilled.setEmailAddress(EMAIL_ADDRESS + "s");
        userTobeFilled.setPassword(PASSWORD);
        userTobeFilled.setGender(GENDER);
        userTobeFilled.setCreationDate(CREATION_DATE);
        userTobeFilled.setAddress(ADDRESS);
        userTobeFilled.setPhoneNumber(PHONE_NUMBER);
        userTobeFilled.setCountry(COUNTRY);
        userTobeFilled.setUsername(USERNAME);
    }
}
