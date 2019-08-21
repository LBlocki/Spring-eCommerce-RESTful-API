package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.api.v1.models.ProductDTO;
import com.blocki.springrestonlinestore.api.v1.models.UserDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.blocki.springrestonlinestore.core.domain.User;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ProductMapperTest {

    private ProductMapper productConverter = ProductMapper.INSTANCE;

    private static final Long productID = 2L;
    private static final String productName = "Doll";
    private static final Product.ProductStatus productStatus = Product.ProductStatus.AVALIABLE;
    private static final String description = "This is the description for the product";
    private static final BigDecimal cost = new BigDecimal(100.2);
    private static final Byte[] photo = {2,3,4};
    private static final String productUrl = "api/v1/product/2";
    private static final LocalDate creationDate = LocalDate.of(2000, 12, 12);

    @Test
    public void productToProductDTO() {

        //given
        Product product = Product.builder()
                .id(productID)
                .name(productName)
                .productStatus(productStatus)
                .description(description)
                .cost(cost)
                .photo(photo)
                .creationDate(creationDate)
                .user(new User())
                .category(new Category())
                .build();

        //when
        ProductDTO productDTO = productConverter.productToProductDTO(product);

        //then
        assertNotNull(productDTO);
        assertNotNull(productDTO.getUserDTO());
        assertNotNull(productDTO.getCategoryDTO());

        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getProductStatus(), product.getProductStatus());
        assertEquals(productDTO.getDescription(), product.getDescription());
        assertEquals(productDTO.getCost(), product.getCost());
        assertArrayEquals(productDTO.getPhoto(), product.getPhoto());
        assertEquals(productDTO.getCreationDate(), product.getCreationDate());

    }

    @Test
    public void productDTOtoProduct() {

        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productID);
        productDTO.setName(productName);
        productDTO.setProductStatus(productStatus);
        productDTO.setDescription(description);
        productDTO.setCost(cost);
        productDTO.setPhoto(photo);
        productDTO.setCreationDate(creationDate);
        productDTO.setUserDTO(new UserDTO());
        productDTO.setCategoryDTO(new CategoryDTO());
        productDTO.setProductUrl(productUrl);

        //when
        Product product = productConverter.productDTOToProduct(productDTO);

        //then
        assertNotNull(product);
        assertNotNull(product.getUser());
        assertNotNull(product.getCategory());

        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getProductStatus(), product.getProductStatus());
        assertEquals(productDTO.getDescription(), product.getDescription());
        assertEquals(productDTO.getCost(), product.getCost());
        assertArrayEquals(productDTO.getPhoto(), product.getPhoto());
        assertEquals(productDTO.getCreationDate(), product.getCreationDate());
    }
}