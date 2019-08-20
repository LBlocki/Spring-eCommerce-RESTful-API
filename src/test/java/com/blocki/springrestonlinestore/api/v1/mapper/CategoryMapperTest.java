package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.CategoryDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryMapperTest {

    private CategoryMapper categoryConverter = CategoryMapper.INSTANCE;

    private static final Long[] CategoryID = {1L, 2L};
    private static final String[] CategoryName = {"Clothes", "Food"};

    private static final Long[] ProductID = {5L, 6L};
    private static final String[] ProductName = {"Salt", "Sand"};

    private Set<Product> products = new HashSet<>();

    @Before
    public void setUp() throws Exception {

        products.add(Product.builder().id(ProductID[0]).name(ProductName[0]).build());
        products.add(Product.builder().id(ProductID[1]).name(ProductName[1]).build());
    }

    @Test
    public void categoryToCategoryDTO() throws Exception {

        //given
        Category category = Category.builder().id(CategoryID[0]).name(CategoryName[0]).products(products).build();

        //when
        CategoryDTO categoryDTO = categoryConverter.categoryToCategoryDTO(category);

        //then
        assertNotNull(categoryDTO);
        assertNotNull(categoryDTO.getProducts());

        assertEquals(categoryDTO.getName(), category.getName());
        assertEquals(categoryDTO.getId(), category.getId());

        assertEquals(categoryDTO.getProducts().size(), category.getProducts().size());

    }
}