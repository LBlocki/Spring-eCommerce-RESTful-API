package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.ProductListDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final UserMapper userConverter = Mappers.getMapper(UserMapper.class);

    private static final Long ID = 2L;
    private static final BigDecimal cost = BigDecimal.ONE;

    private Product product = Product.builder().cost(cost).id(ID).build();

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllProducts() {

        //given
        List<Product> products = Arrays.asList(new Product(), new Product());
        Mockito.when(productRepository.findAll()).thenReturn(products);

        //when
        ProductListDTO productListDTO = productServiceImpl.getAllProducts();

        //than
        assertNotNull(productListDTO);

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getProductById() {

        //given
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));

        //when
        ProductDTO userDTO = productServiceImpl.getProductById(ID);

        //than
        assertNotNull(userDTO);
        assertEquals(userDTO.getId(), product.getId());
        assertEquals(userDTO.getCost(), product.getCost());

        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void saveProduct() {

        //given
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        //when
        ProductDTO savedProductDTO = productServiceImpl.saveProduct(productConverter.productToProductDTO(product));

        //than
        assertNotNull(savedProductDTO);
        assertEquals(savedProductDTO.getId(), product.getId());
        assertEquals(savedProductDTO.getCost(), product.getCost());

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void createNewProduct() {

        //given
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        //when
        ProductDTO createdProductDTO = productServiceImpl.createNewProduct(productConverter.productToProductDTO(product));

        //than
        assertNotNull(createdProductDTO);
        assertEquals(createdProductDTO.getId(), product.getId());
        assertEquals(createdProductDTO.getCost(), product.getCost());

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void deleteProductById() {

        //when
        productServiceImpl.deleteProductById(ID);

        //than
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}