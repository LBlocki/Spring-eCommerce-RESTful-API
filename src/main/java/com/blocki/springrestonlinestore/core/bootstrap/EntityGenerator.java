package com.blocki.springrestonlinestore.core.bootstrap;


import com.blocki.springrestonlinestore.core.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

class EntityGenerator {

    // Remember that this class does not set fields that contain references to other objects

    private Random randomGenerator = new Random();

    //users data
    private static final Long[] userIds = {1L};
    private static final String[] firstNames ={ "Michael", "Stephen", "Mia", "Patrick", "Amy"};
    private static final String[] lastNames = {"Borrows", "Angelo", "Scalleta", "Hawking", "Beethoven"};
    private static final String[] addresses = {"221B Baker Street", "Modlinska 44", "Champs-Élysées 22A",
                                    "Lombard Street 12", "Fifth Avenue 112V"};
    private static final String[] countries = {"Great Britain", "Scotland", "Poland", "USA", "Germany"};
    private static final String[] phoneNumbers = {"213122112", "472640517", "284067253", "037185923", "847517005"};
    private static final String[] emailAddresses = {"myEmailAddress@gmail.com", "Angelina@gmail.com",
            "giveMeBabyOneMoreTime@gmail.com", "allNamesTaken@o2.pl", "dontRemember@wp.pl"};
    private static final String[] usernames = {"PolishBoy21", "UsernameLOL", "DontRememberName", "DoctorX", "Psycho"};
    private static final char[][] passwords = {{'q','s','t'}, {'s', '2', '5', ','},
            {'q','v','x'},{'q','s','t', 't', 't'},{'a','s','s', 'q', '1'}};
    private static final User.Gender[] genders = {User.Gender.MALE, User.Gender.FEMALE};

    //products data
    private static final Long[] productIDs = {1L};
    private static final String[] productNames = {"Doll 300", "Ram 128 Gigabytes", "Witcher 3",
            "SSD drive 512 G", "Samsung galaxy S40"};
    private static final Product.ProductStatus[] productStatuses = {Product.ProductStatus.AVALIABLE,
            Product.ProductStatus.OUT_OF_STOCK};
    private static final String[] descriptions = {"Awesome, only opened once",
            "Best for birthday present", "Im selling because it's just too good",
            "Small defect, so price is much lower", "It's so amazing that it does not need description"};
    private static final BigDecimal[] costs = {new BigDecimal(100.21),
            new BigDecimal(30.20),new BigDecimal(1220.82),new BigDecimal(83.72),new BigDecimal(95.57)};
    private static final Byte[][] photos = {{2,3,8},{5,7,2,3},{9,8,7,2,4},{1,7,3,4,78},{2,3,1, 5, 7}};

    //orders data
    private static final Long[] orderIds = {1L};
    private static final Order.OrderStatus[] orderStatuses ={ Order.OrderStatus.ACTIVE, Order.OrderStatus.COMPLETED};

    //order items data
    private static final Long[] orderItemIds = {1L, 2L, 3L, 4L, 5L};
    private static final Integer[] quantities ={ 10, 54, 1, 9, 17};
    private static final BigDecimal[] totalCosts ={ new BigDecimal(20.1), new BigDecimal(50.62),
            new BigDecimal(192.12), new BigDecimal(64.69), new BigDecimal(01.99)};

    //categories data
    private static final Long[] categoryIDs = {1L};
    private static final String[] categoryNames = {"Clothes", "Electronics", "Smart Tv's", "Music", "Games"};


    User generateUser() {

        User user = new User();

        user.setId(userIds[randomGenerator.nextInt(userIds.length)]);
        user.setFirstName(firstNames[randomGenerator.nextInt(firstNames.length)]);
        user.setLastName(lastNames[randomGenerator.nextInt(lastNames.length)]);
        user.setAddress(addresses[randomGenerator.nextInt(addresses.length)]);
        user.setCountry(countries[randomGenerator.nextInt(countries.length)]);
        user.setPhoneNumber(phoneNumbers[randomGenerator.nextInt(phoneNumbers.length)]);
        user.setCreationDate(LocalDate.now());
        user.setEmailAddress(emailAddresses[randomGenerator.nextInt(emailAddresses.length)]
                + randomGenerator.nextInt(30)
                +(user.getFirstName().length() * user.getLastName().length() * user.getId().intValue()));
        user.setUsername(usernames[randomGenerator.nextInt(usernames.length)]+ randomGenerator.nextInt(30)
                +(user.getFirstName().length() * user.getLastName().length() * user.getId().intValue()));
        user.setPassword(passwords[randomGenerator.nextInt(passwords.length)]);
        user.setGender(genders[randomGenerator.nextInt(genders.length)]);

        return user;
    }

    Product generateProduct() {

        Product product = new Product();

        product.setCreationDate(LocalDate.now());
        product.setCost(costs[randomGenerator.nextInt(costs.length)]);
        product.setProductStatus(productStatuses[randomGenerator.nextInt(productStatuses.length)]);
        product.setId(productIDs[randomGenerator.nextInt(productIDs.length)]);
        product.setDescription(descriptions[randomGenerator.nextInt(descriptions.length)]);
        product.setPhoto(photos[randomGenerator.nextInt(photos.length)]);
        product.setName(productNames[randomGenerator.nextInt(productNames.length)]+ randomGenerator.nextInt(10)
                *(product.getDescription().length() * randomGenerator.nextInt(30) + product.getId().intValue()));

        return product;
    }

    Order generateOrder() {

        Order order = new Order();

        order.setId(orderIds[randomGenerator.nextInt(orderIds.length)]);
        order.setCreationDate(LocalDate.now());
        order.setOrderStatus(orderStatuses[randomGenerator.nextInt(orderStatuses.length)]);

        return order;
    }

    OrderItem generateOrderItem() {

        OrderItem orderItem = new OrderItem();

        orderItem.setId(orderItemIds[randomGenerator.nextInt(orderItemIds.length)]);
        orderItem.setQuantity(quantities[randomGenerator.nextInt(quantities.length)]);
        orderItem.setTotalCost(totalCosts[randomGenerator.nextInt(totalCosts.length)]);

        return orderItem;
    }

    Category generateCategory() {

        Category category = new Category();

        category.setId(categoryIDs[randomGenerator.nextInt(categoryIDs.length)]);
        category.setName(categoryNames[randomGenerator.nextInt(categoryNames.length)]+ randomGenerator.nextInt()
                * category.getId().intValue() + randomGenerator.nextInt(30));

        return category;
    }
}
