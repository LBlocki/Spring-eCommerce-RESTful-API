package com.blocki.springecommerceapp.core.services;

import com.blocki.springecommerceapp.api.v1.controllers.UserController;
import com.blocki.springecommerceapp.api.v1.mappers.OrderMapper;
import com.blocki.springecommerceapp.api.v1.mappers.ProductMapper;
import com.blocki.springecommerceapp.api.v1.mappers.UserMapper;
import com.blocki.springecommerceapp.api.v1.models.OrderDTO;
import com.blocki.springecommerceapp.api.v1.models.ProductDTO;
import com.blocki.springecommerceapp.api.v1.models.UserDTO;
import com.blocki.springecommerceapp.core.config.resourceAssemblers.OrderResourceAssembler;
import com.blocki.springecommerceapp.core.config.resourceAssemblers.ProductResourceAssembler;
import com.blocki.springecommerceapp.core.config.resourceAssemblers.UserResourceAssembler;
import com.blocki.springecommerceapp.core.domain.User;
import com.blocki.springecommerceapp.core.exceptions.NotFoundException;
import com.blocki.springecommerceapp.core.exceptions.ResourceAlreadyExistsException;
import com.blocki.springecommerceapp.core.repositories.OrderRepository;
import com.blocki.springecommerceapp.core.repositories.ProductRepository;
import com.blocki.springecommerceapp.core.repositories.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final OrderMapper orderConverter = Mappers.getMapper(OrderMapper.class);

    private final UserResourceAssembler userResourceAssembler;
    private final ProductResourceAssembler productResourceAssembler;
    private final OrderResourceAssembler orderResourceAssembler;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository,
                           ProductRepository productRepository,UserResourceAssembler userResourceAssembler,
                           ProductResourceAssembler productResourceAssembler,
                           OrderResourceAssembler orderResourceAssembler) {

        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userResourceAssembler = userResourceAssembler;
        this.productResourceAssembler = productResourceAssembler;
        this.orderResourceAssembler = orderResourceAssembler;
    }

    @Override
    public Resources<Resource<UserDTO>> getAllUsers() {

        List<Resource<UserDTO>> users = userRepository
                .findAll()
                .stream()
                .map(userConverter::userToUserDTO)
                .map(userResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class)
                        .createNewUser(new UserDTO())).withRel("create new user").withType("POST"),
                linkTo(methodOn(UserController.class)
                        .getAllUsers()).withSelfRel().withType("GET"));
    }

    @Override
    public Resource<UserDTO> getUserById(Long id) {

        UserDTO userDTO =  userRepository
                .findById(id)
                .map(userConverter::userToUserDTO)
                .orElseThrow(NotFoundException::new);

        return userResourceAssembler.toResource(userDTO);
    }

    @Override
    public Resource<UserDTO> getUserByUsername(String username) {

        UserDTO userDTO = userRepository
                .findUserByUsername(username)
                .map(userConverter::userToUserDTO)
                .orElseThrow(NotFoundException::new);

        return userResourceAssembler.toResource(userDTO);
    }

    private Resource<UserDTO> saveUser(UserDTO userDTO) {

        User user = userRepository.save(userConverter.userDTOToUser(userDTO));

        return userResourceAssembler.toResource(userConverter.userToUserDTO(user));
    }

    @Override
    public Resource<UserDTO> createNewUser(UserDTO userDTO) {

        if(userDTO.getUsername() != null && userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {

            throw new ResourceAlreadyExistsException("User with this username value already exists");
        }

       return saveUser(userDTO);
    }

    @Override
    public Resource<UserDTO> updateUser(Long id, UserDTO userDTO) {

        User user = userConverter.userDTOToUser(userDTO);
        user.setId(id);

        return saveUser(userConverter.userToUserDTO(user));
    }


    @Override
    public Resource<UserDTO> patchUser(Long id, UserDTO userDTO) {

       return userRepository
               .findById(id)
               .map(user -> {

                   if(userDTO.getAddress() != null) {

                       user.setAddress(userDTO.getAddress());
                   }

                   if(userDTO.getCountry() != null) {

                       user.setCountry(userDTO.getCountry());
                   }

                   if(userDTO.getCreationDate() != null) {

                       user.setCreationDate(userDTO.getCreationDate());
                   }

                   if(userDTO.getEmailAddress() != null) {

                       user.setEmailAddress(userDTO.getEmailAddress());
                   }

                   if(userDTO.getFirstName() != null) {

                       user.setFirstName(userDTO.getFirstName());
                   }

                   if(userDTO.getGender() != null) {

                       user.setGender(userDTO.getGender());
                   }

                   if(userDTO.getLastName() != null) {

                       user.setLastName(userDTO.getLastName());
                   }

                   if(userDTO.getPassword() != null) {

                       user.setPassword(userDTO.getPassword());
                   }

                   if(userDTO.getPhoneNumber() != null) {

                       user.setPhoneNumber(userDTO.getPhoneNumber());
                   }

                   if(userDTO.getUsername() != null) {

                       user.setUsername(userDTO.getUsername());
                   }

                   if(user.getOrder() != null) {

                       orderRepository.deleteById(user.getOrder().getId());
                   }

                   user.setOrder(orderConverter.orderDTOToOrder(userDTO.getOrderDTO()));

                   if(user.getOrder() == null) {

                       orderRepository.save(user.getOrder());
                   }

                   return saveUser(userConverter.userToUserDTO(user));
               })
               .orElseThrow(NotFoundException::new);

    }

    @Override
    public void deleteUserById(Long id) {

        userRepository.deleteById(id);
    }

    @Override
    public Resource<OrderDTO> createNewOrder(Long id, OrderDTO orderDTO) {

        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        UserDTO userDTO = userConverter.userToUserDTO(user);

        if(userDTO.getOrderDTO() != null) {

            throw new ResourceAlreadyExistsException("Order already exists. You need to delete current order before creating a new one");
        }

        orderDTO.setUserDTO(userDTO);
        orderDTO.setUserDTOId(userDTO.getId());
        orderDTO = orderConverter.orderToOrderDTO(orderRepository.save(orderConverter.orderDTOToOrder(orderDTO)));

        userDTO.setOrderDTO(orderDTO);
        saveUser(userDTO);

        return orderResourceAssembler.toResource(orderDTO);
    }

    @Override
    public  Resource<ProductDTO> createNewProduct(Long id, ProductDTO productDTO) {

        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        UserDTO userDTO = userConverter.userToUserDTO(user);

        if(Objects.requireNonNull(userDTO.getProductDTOs()).contains(productDTO)) {

            throw new ResourceAlreadyExistsException("Product is already assigned to this user");
        }

        productDTO.setUserDTO(userDTO);
        productDTO.setUserDTOId(userDTO.getId());
        productDTO = productConverter.productToProductDTO(productRepository
                .save(productConverter.productDTOToProduct(productDTO)));

        Objects.requireNonNull(userDTO.getProductDTOs()).add(productDTO);
        saveUser(userDTO);

        return productResourceAssembler.toResource(productDTO);
    }

    @Override
    public Resources<Resource<ProductDTO>> getAllProducts(Long id) {

        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        UserDTO userDTO = userConverter.userToUserDTO(user);

        List<Resource<ProductDTO>> products = Objects
                .requireNonNull(userDTO
                .getProductDTOs())
                .stream()
                .map(productResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(products ,
                linkTo(methodOn(UserController.class).getUserById(id)).withRel("get product's user").withType("GET"),
                linkTo(methodOn(UserController.class).getAllUsersProducts(id)).withSelfRel().withType("GET"));
    }

    @Override
    public  Resource<OrderDTO> getOrderById(Long id) {

        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);

        if(user.getOrder() == null) {

            throw new NotFoundException("User's shopping cart is null");
        }

        return orderResourceAssembler.
                toResource(orderConverter.orderToOrderDTO(user.getOrder()));
    }
}
