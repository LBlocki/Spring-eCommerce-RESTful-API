package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.CategoryDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryMapperTest {

    private CategoryMapper categoryConverter = CategoryMapper.INSTANCE;

    private static final Long CategoryID = 1L;
    private static final String CategoryName = "Clothes";

    @Test
    public void categoryToCategoryDTO() {

        //given
        Category category = Category.builder()
                .id(CategoryID)
                .name(CategoryName)
                .build();

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
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(CategoryID);
        categoryDTO.setName(CategoryName);

        //when
        Category category = categoryConverter.categoryDTOtoCategory(categoryDTO);

        //then
        assertNotNull(category);

        assertEquals(category.getName(), categoryDTO.getName());
        assertEquals(category.getId(), categoryDTO.getId());
    }
}