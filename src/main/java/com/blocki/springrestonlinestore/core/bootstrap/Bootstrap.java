package com.blocki.springrestonlinestore.core.bootstrap;

import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import com.blocki.springrestonlinestore.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
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
    public Bootstrap(CategoryRepository categoryRepository, UserRepository userRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;

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

        fillUser(firstUser);

        User secondUser = new User();

        fillUser(secondUser);
        secondUser.setEmailAddress(EMAIL_ADDRESS + "f");
        secondUser.setUsername(USERNAME + "f");
        userService.createNewUser(UserMapper.INSTANCE.userToUserDTO(firstUser));
        userService.createNewUser(UserMapper.INSTANCE.userToUserDTO(secondUser));

    }

    private void fillUser(User firstUser) {


        firstUser.setFirstName(FIRST_NAME);
        firstUser.setLastName(LAST_NAME);
        firstUser.setEmailAddress(EMAIL_ADDRESS + "s");
        firstUser.setPassword(PASSWORD);
        firstUser.setGender(GENDER);
        firstUser.setCreationDate(CREATION_DATE);
        firstUser.setAddress(ADDRESS);
        firstUser.setPhoneNumber(PHONE_NUMBER);
        firstUser.setCountry(COUNTRY);
        firstUser.setUsername(USERNAME);
        firstUser.setProducts(new ArrayList<>());
        firstUser.setShoppingCart(new ShoppingCart());
    }
}
