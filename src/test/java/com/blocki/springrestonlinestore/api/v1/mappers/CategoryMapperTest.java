package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryMapperTest {

    private CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    private static final Long categoryID = 1L;
    private static final String categoryName = "Clothes";
    private Category category = new Category();

    @Before
    public void setUp() {

        category.setId(categoryID);
        category.setName(categoryName);
    }
    @Test
    public void categoryToCategoryDTO() {

        //when
        CategoryDTO categoryDTO = categoryConverter.categoryToCategoryDTO(category);

        //then
        assertNotNull(categoryDTO);

        assertEquals(categoryDTO.getName(), category.getName());
        assertEquals(categoryDTO.getId(), category.getId());

    }

    @Test
    public void categoryDTOToCategory() {

        //given
        CategoryDTO categoryDTO = categoryConverter.categoryToCategoryDTO(category);

        //when
        Category category = categoryConverter.categoryDTOtoCategory(categoryDTO);

        //then
        assertNotNull(category);

        assertEquals(category.getName(), categoryDTO.getName());
        assertEquals(category.getId(), categoryDTO.getId());
    }
}