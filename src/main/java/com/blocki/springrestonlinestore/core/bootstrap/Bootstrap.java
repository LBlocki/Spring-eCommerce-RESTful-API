package com.blocki.springrestonlinestore.core.bootstrap;

import com.blocki.springrestonlinestore.api.v1.mappers.OrderItemMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.OrderMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.OrderDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.*;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import com.blocki.springrestonlinestore.core.services.OrderService;
import com.blocki.springrestonlinestore.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Component
@Transactional
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final OrderService orderService;

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final OrderMapper orderConverter = Mappers.getMapper(OrderMapper.class);
    private final OrderItemMapper orderItemConverter = Mappers.getMapper(OrderItemMapper.class);

    private Category category;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, UserService userService, OrderService orderService) {

        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {

        createUser(1,1);
        createUser(2,1);
        createUser(1,2);
    }
    void createUser(int amountOfProducts, int amountOfOrderItems) {

        EntityGenerator entityGenerator = new EntityGenerator(2 * amountOfOrderItems + amountOfProducts + 10);

        category = entityGenerator.generateCategory();
        category = categoryRepository.save(category);

        User user = entityGenerator.generateUser();
        user.setOrder(null);

        UserDTO savedUserDTO = userService.createNewUser(userConverter.userToUserDTO(user)).getContent();

        for(int i = 0; i < amountOfProducts; i++) {

            Product product = addProduct(userConverter.userDTOToUser(savedUserDTO), i);

            userService.createNewProduct(savedUserDTO.getId(), productConverter.productToProductDTO(product));
        }

        savedUserDTO = userService.getUserById(savedUserDTO.getId()).getContent();

        if(amountOfOrderItems > 0) {

            Order order = entityGenerator.generateOrder();
            OrderDTO savedOrderDTO = userService
                    .createNewOrder(savedUserDTO.getId(), orderConverter.orderToOrderDTO(order)).getContent();
            savedUserDTO = userService.getUserById(savedUserDTO.getId()).getContent();

            for(int i = 0; i < amountOfOrderItems; i++) {

                UserDTO savedExtraUser = createExtraUser(i + amountOfProducts + 1);

                OrderItem orderItem = entityGenerator.generateOrderItem();
                orderItem.setProduct(productConverter
                        .productDTOToProduct(Objects.requireNonNull(savedExtraUser.getProductDTOs()).get(0)));

                orderService.createNewOrderItem(savedOrderDTO.getId(), orderItemConverter.orderItemToOrderItemDTO(orderItem));
                savedOrderDTO = orderService.getOrderById(savedOrderDTO.getId()).getContent();
            }

            savedUserDTO.setOrderDTO(orderService.getOrderById(savedOrderDTO.getId()).getContent());

            userService.patchUser(savedUserDTO.getId(), savedUserDTO);
        }

    }

    Product addProduct(User user, Integer primeNumber) {

        EntityGenerator entityGenerator = new EntityGenerator(primeNumber);

        Product product = entityGenerator.generateProduct();

        while(user.getProducts().contains(product)) {
            product = entityGenerator.generateProduct();
        }

        product.setCategory(category);

        return product;
    }

    UserDTO createExtraUser(Integer primeNumber) {

        EntityGenerator entityGenerator = new EntityGenerator(primeNumber);

        User user = entityGenerator.generateUser();

        user.setOrder(null);

        Product extraProduct = addProduct(user, primeNumber);

        UserDTO savedExtraUserDTO =  userService.createNewUser(userConverter.userToUserDTO(user)).getContent();

        userService.createNewProduct(savedExtraUserDTO.getId(), productConverter.productToProductDTO(extraProduct));

        return userService.getUserById(savedExtraUserDTO.getId()).getContent();
    }
}
