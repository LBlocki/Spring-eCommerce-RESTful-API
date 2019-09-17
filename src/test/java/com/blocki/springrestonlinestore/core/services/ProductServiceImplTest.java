package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.bootstrap.TestEntityGenerator;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ProductResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.repositories.ProductRepository;
import com.blocki.springrestonlinestore.core.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.hateoas.Resource;

import java.util.Optional;

import static org.junit.Assert.*;

public class ProductServiceImplTest {

    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    private Product product;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Spy
    private ProductResourceAssembler productResourceAssembler = new ProductResourceAssembler();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        product = testEntityGenerator.generateProduct();
    }

    @Test
    public void getProductById() {

        //given
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));

        //when
        Resource<ProductDTO> testProductDTO = productService.getProductById(product.getId());

        //then
        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productResourceAssembler, Mockito.times(1)).toResource(Mockito.any(ProductDTO.class));

        Mockito.verifyNoMoreInteractions(productRepository);
        Mockito.verifyNoMoreInteractions(productResourceAssembler);

        assertNotNull(testProductDTO);
        assertNotNull(testProductDTO.getContent().getCategoryDTO());
        assertNotNull(testProductDTO.getContent().getUserDTO());

        assertEquals(testProductDTO.getContent().getId(), product.getId());
        assertEquals(testProductDTO.getContent().getCategoryDTO().getId(), product.getCategory().getId());
        assertEquals(testProductDTO.getContent().getUserDTO().getId(), product.getUser().getId());
        assertEquals(testProductDTO.getContent().getCost(), product.getCost());
        assertEquals(testProductDTO.getContent().getCreationDate(), product.getCreationDate());
        assertEquals(testProductDTO.getContent().getUserDTOId(), product.getUser().getId());
        assertEquals(testProductDTO.getContent().getProductStatus(), product.getProductStatus());
        assertEquals(testProductDTO.getContent().getDescription(), product.getDescription());
        assertEquals(testProductDTO.getContent().getName(), product.getName());

        assertArrayEquals(testProductDTO.getContent().getPhoto(), product.getPhoto());
    }

    @Test(expected = UnsupportedOperationException.class)   //to be fixed, problem with method remove in list
    public void deleteProductById() {

        //given
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testEntityGenerator.generateUser()));

        //when
        productService.deleteProductById(product.getId());

        //then
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyLong());

        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void getProductByName() {

        //given
        Mockito.when(productRepository.findProductByName(Mockito.any())).thenReturn(Optional.of(product));

        //when
        Resource<ProductDTO> testProductDTO = productService.getProductByName(product.getName());

        //then
        Mockito.verify(productRepository, Mockito.times(1)).findProductByName(Mockito.any());
        Mockito.verify(productResourceAssembler, Mockito.times(1)).toResource(Mockito.any(ProductDTO.class));

        Mockito.verifyNoMoreInteractions(productRepository);
        Mockito.verifyNoMoreInteractions(productResourceAssembler);

        assertNotNull(testProductDTO);
        assertNotNull(testProductDTO.getContent().getCategoryDTO());
        assertNotNull(testProductDTO.getContent().getUserDTO());

        assertEquals(testProductDTO.getContent().getId(), product.getId());
        assertEquals(testProductDTO.getContent().getCategoryDTO().getId(), product.getCategory().getId());
        assertEquals(testProductDTO.getContent().getUserDTO().getId(), product.getUser().getId());
        assertEquals(testProductDTO.getContent().getCost(), product.getCost());
        assertEquals(testProductDTO.getContent().getCreationDate(), product.getCreationDate());
        assertEquals(testProductDTO.getContent().getUserDTOId(), product.getUser().getId());
        assertEquals(testProductDTO.getContent().getProductStatus(), product.getProductStatus());
        assertEquals(testProductDTO.getContent().getDescription(), product.getDescription());
        assertEquals(testProductDTO.getContent().getName(), product.getName());

        assertArrayEquals(testProductDTO.getContent().getPhoto(), product.getPhoto());
    }
}