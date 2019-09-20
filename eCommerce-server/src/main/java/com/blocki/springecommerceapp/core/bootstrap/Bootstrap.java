package com.blocki.springecommerceapp.core.bootstrap;

import com.blocki.springecommerceapp.api.v1.mappers.OrderItemMapper;
import com.blocki.springecommerceapp.api.v1.mappers.OrderMapper;
import com.blocki.springecommerceapp.api.v1.mappers.ProductMapper;
import com.blocki.springecommerceapp.api.v1.mappers.UserMapper;
import com.blocki.springecommerceapp.api.v1.models.OrderDTO;
import com.blocki.springecommerceapp.api.v1.models.UserDTO;
import com.blocki.springecommerceapp.core.domain.*;
import com.blocki.springecommerceapp.core.repositories.CategoryRepository;
import com.blocki.springecommerceapp.core.services.OrderService;
import com.blocki.springecommerceapp.core.services.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
@Transactional
@Profile("dev")
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

        if(userService.getAllUsers().getContent().size() == 0) {

            createUser();
        }
    }
    void createUser() {

        EntityGenerator entityGenerator = new EntityGenerator();

        category = entityGenerator.generateCategory();
        category = categoryRepository.save(category);

        User user = entityGenerator.generateUser();
        user.setOrder(null);

        UserDTO savedUserDTO = userService.createNewUser(userConverter.userToUserDTO(user)).getContent();

        Product product = addProduct(userConverter.userDTOToUser(savedUserDTO));

        userService.createNewProduct(savedUserDTO.getId(), productConverter.productToProductDTO(product));

        savedUserDTO = userService.getUserById(savedUserDTO.getId()).getContent();

        Order order = entityGenerator.generateOrder();
        OrderDTO savedOrderDTO = userService
                .createNewOrder(savedUserDTO.getId(), orderConverter.orderToOrderDTO(order)).getContent();
        savedUserDTO = userService.getUserById(savedUserDTO.getId()).getContent();

        UserDTO savedExtraUser = createExtraUser();

        OrderItem orderItem = entityGenerator.generateOrderItem();
        orderItem.setProduct(productConverter
                .productDTOToProduct(Objects.requireNonNull(savedExtraUser.getProductDTOs()).get(0)));

        orderService.createNewOrderItem(savedOrderDTO.getId(), orderItemConverter.orderItemToOrderItemDTO(orderItem));
        savedOrderDTO = orderService.getOrderById(savedOrderDTO.getId()).getContent();

        savedUserDTO.setOrderDTO(orderService.getOrderById(savedOrderDTO.getId()).getContent());

        userService.patchUser(savedUserDTO.getId(), savedUserDTO);
    }

    Product addProduct(User user) {

        EntityGenerator entityGenerator = new EntityGenerator();

        Product product = entityGenerator.generateProduct();

        while(user.getProducts().contains(product)) {
            product = entityGenerator.generateProduct();
        }

        product.setCategory(category);

        return product;
    }

    UserDTO createExtraUser() {

        EntityGenerator entityGenerator = new EntityGenerator();

        User user = entityGenerator.generateUser();

        user.setOrder(null);

        Product extraProduct = addProduct(user);

        UserDTO savedExtraUserDTO =  userService.createNewUser(userConverter.userToUserDTO(user)).getContent();

        userService.createNewProduct(savedExtraUserDTO.getId(), productConverter.productToProductDTO(extraProduct));

        return userService.getUserById(savedExtraUserDTO.getId()).getContent();
    }
}
