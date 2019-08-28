package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.ProductMapper;
import com.blocki.springrestonlinestore.api.v1.mappers.UserMapper;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.Resource;

import java.math.BigDecimal;
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
    public void getProductById() {

        //given
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));

        //when
        Resource<ProductDTO> userDTO = productServiceImpl.getProductById(ID);

        //than
        assertNotNull(userDTO);
        assertEquals(userDTO.getContent().getId(), product.getId());
        assertEquals(userDTO.getContent().getCost(), product.getCost());

        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void deleteProductById() {

        //when
        productServiceImpl.deleteProductById(ID);

        //than
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}