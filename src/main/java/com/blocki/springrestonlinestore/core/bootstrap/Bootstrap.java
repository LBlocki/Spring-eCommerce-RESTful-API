package com.blocki.springrestonlinestore.core.bootstrap;

import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import com.blocki.springrestonlinestore.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    public void run(String... args) {




       loadUsers();


    }

    private void loadUsers() {

        Category clothes = new Category();
        Category food =  new Category();

        clothes.setId(1L);
        clothes.setName("Clothes");
        food.setId(2L);
        food.setName("Food");

        categoryRepository.save(clothes);
        categoryRepository.save(food);

        User firstUser = new User();
        firstUser.setId(1L);
        fillUser(firstUser);

        Product product = new Product();
        product.setUser(firstUser);
        product.setCategory(clothes);
        product.setCreationDate(LocalDate.now());
        product.setCost(BigDecimal.ONE);
        product.setProductStatus(Product.ProductStatus.AVALIABLE);
        product.setName("Product name");
        product.setId(ID);
        product.setDescription("This is description");
        product.setPhoto(new Byte[]{'s'});

        firstUser.getProducts().add(product);

        User secondUser = new User();
        secondUser.setId(2L);
        fillUser(secondUser);

        secondUser.setEmailAddress(EMAIL_ADDRESS + "f");
        secondUser.setUsername(USERNAME + "f");

        userService.createNewUser(Mappers.getMapper(UserMapper.class).userToUserDTO(firstUser));
        userService.createNewUser(Mappers.getMapper(UserMapper.class).userToUserDTO(secondUser));

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
