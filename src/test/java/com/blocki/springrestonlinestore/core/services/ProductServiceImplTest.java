package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.ProductResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.hateoas.Resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductServiceImplTest {


    private static final Long productID = 2L;
    private static final Long userID = 5L;
    private static final String productName = "Doll";
    private static final Product.ProductStatus productStatus = Product.ProductStatus.AVALIABLE;
    private static final String description = "This is the description for the product";
    private static final BigDecimal cost = new BigDecimal(100.2);
    private static final Byte[] photo = {2,3,4};
    private static final LocalDate creationDate = LocalDate.of(2000, 12, 12);

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductResourceAssembler productResourceAssembler = new ProductResourceAssembler();

    @InjectMocks
    private ProductServiceImpl productService;

    private User user = new User();
    private Product product = new Product();
    private Category category = new Category();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        user.setId(userID);
        category.setId(3L);
        category.setName("Clothes");

        product.setUser(user);
        product.setCategory(category);
        product.setCreationDate(creationDate);
        product.setCost(cost);
        product.setProductStatus(productStatus);
        product.setName(productName);
        product.setId(productID);
        product.setDescription(description);
        product.setPhoto(photo);
    }

    @Test
    public void getProductById() {

        //given
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));

        //when
        Resource<ProductDTO> productDTO = productService.getProductById(productID);

        //than
        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productResourceAssembler, Mockito.times(1)).toResource(Mockito.any(ProductDTO.class));

        assertNotNull(productDTO);
        assertEquals(productDTO.getContent().getId(), product.getId());
        assertEquals(productDTO.getContent().getUserDTOId(), userID);
        assertEquals(productDTO.getContent().getProductStatus(), product.getProductStatus());
    }

    @Test
    public void deleteProductById() {

        productService.deleteProductById(productID);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void getProductByName() {

        //given
        Mockito.when(productRepository.findProductByName(Mockito.any())).thenReturn(Optional.of(product));

        //when
        Resource<ProductDTO> productDTO = productService.getProductByName(product.getName());

        //than
        Mockito.verify(productRepository, Mockito.times(1)).findProductByName(Mockito.any());
        Mockito.verify(productResourceAssembler, Mockito.times(1)).toResource(Mockito.any(ProductDTO.class));

        assertNotNull(productDTO);
        assertEquals(productDTO.getContent().getId(), product.getId());
        assertEquals(productDTO.getContent().getUserDTOId(), userID);
        assertEquals(productDTO.getContent().getProductStatus(), product.getProductStatus());
    }
}