package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.controllers.UserController;
import com.blocki.springrestonlinestore.api.v1.mappers.OrderMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.OrderDTO;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.OrderResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ProductResourceAssembler;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.UserResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.exceptions.ResourceAlreadyExistsException;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);
    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final OrderMapper orderConverter = Mappers.getMapper(OrderMapper.class);

    private final UserResourceAssembler userResourceAssembler;
    private final ProductResourceAssembler productResourceAssembler;
    private final OrderResourceAssembler orderResourceAssembler;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserResourceAssembler userResourceAssembler,
                           ProductResourceAssembler productResourceAssembler, OrderResourceAssembler orderResourceAssembler) {

        this.userRepository = userRepository;
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

        if(userDTO.getId() != null && userRepository.findById(userDTO.getId()).isPresent()) {

            throw new ResourceAlreadyExistsException("User with this Id value already exists");
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

                   if(userDTO.getProductDTOs() != null) {

                       List<Product> products = new ArrayList<>();

                       for( ProductDTO productDTO : userDTO.getProductDTOs()) {

                           products.add(productConverter.productDTOToProduct(productDTO));
                       }

                       user.setProducts(products);

                   }

                   if(userDTO.getOrderDTO() != null) {

                      user.setOrder(orderConverter.orderDTOToOrder(userDTO.getOrderDTO()));
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

        userDTO.setOrderDTO(orderDTO);
        orderDTO.setUserDTO(userDTO);
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

        Objects.requireNonNull(userDTO.getProductDTOs()).add(productDTO);
        productDTO.setUserDTOId(userDTO.getId());
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
