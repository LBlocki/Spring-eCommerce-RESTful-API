package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.TestEntity;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.core.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.*;

public class ProductMapperTest {

    private final ProductMapper productConverter = Mappers.getMapper(ProductMapper.class);
    private final TestEntity testEntity = new TestEntity();

    private Product product;

    @Before
    public void setUp() {

        product = testEntity.getProduct();
    }

    @Test
    public void productToProductDTO() {

        //when
        ProductDTO testProductDTO = productConverter.productToProductDTO(product);

        //then
        assertNotNull(testProductDTO);
        assertNotNull(testProductDTO.getUserDTO());
        assertNotNull(testProductDTO.getCategoryDTO());

        assertEquals(testProductDTO.getId(), product.getId());
        assertEquals(testProductDTO.getName(), product.getName());
        assertEquals(testProductDTO.getProductStatus(), product.getProductStatus());
        assertEquals(testProductDTO.getDescription(), product.getDescription());
        assertEquals(testProductDTO.getCost(), product.getCost());
        assertEquals(testProductDTO.getCreationDate(), product.getCreationDate());
        assertEquals(testProductDTO.getUserDTO().getId(), product.getUser().getId());
        assertEquals(testProductDTO.getCategoryDTO().getId(), product.getCategory().getId());
        assertEquals(testProductDTO.getUserDTOId(), product.getUser().getId());

        assertArrayEquals(testProductDTO.getPhoto(), product.getPhoto());

    }

    @Test
    public void productDTOtoProduct() {

        //given
        ProductDTO productDTO = productConverter.productToProductDTO(product);

        //when
        Product testProduct = productConverter.productDTOToProduct(productDTO);

        //then
        assertNotNull(testProduct);
        assertNotNull(testProduct.getUser());
        assertNotNull(testProduct.getCategory());

        assertEquals(testProduct.getId(), productDTO.getId());
        assertEquals(testProduct.getName(), productDTO.getName());
        assertEquals(testProduct.getProductStatus(), productDTO.getProductStatus());
        assertEquals(testProduct.getDescription(), productDTO.getDescription());
        assertEquals(testProduct.getCost(), productDTO.getCost());
        assertEquals(testProduct.getCreationDate(), productDTO.getCreationDate());
        assertEquals(testProduct.getUser().getId(), productDTO.getUserDTO().getId());
        assertEquals(testProduct.getCategory().getId(), productDTO.getCategoryDTO().getId());

        assertArrayEquals(testProduct.getPhoto(), productDTO.getPhoto());
    }
}