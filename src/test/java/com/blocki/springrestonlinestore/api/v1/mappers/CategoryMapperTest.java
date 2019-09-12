package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.core.bootstrap.TestEntityGenerator;
import com.blocki.springrestonlinestore.core.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryMapperTest {

    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);
    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    private Category category;

    @Before
    public void setUp() {

        category = testEntityGenerator.generateCategory();
    }

    @Test
    public void categoryToCategoryDTO() {

        //when
        CategoryDTO testCategoryDTO = categoryConverter.categoryToCategoryDTO(category);

        //then
        assertNotNull(testCategoryDTO);

        //then
        assertEquals(testCategoryDTO.getName(), category.getName());
        assertEquals(testCategoryDTO.getId(), category.getId());
    }

    @Test
    public void categoryDTOToCategory() {

        //given
        CategoryDTO categoryDTO = categoryConverter.categoryToCategoryDTO(category);

        //when
        Category testCategory = categoryConverter.categoryDTOtoCategory(categoryDTO);

        //then
        assertNotNull(testCategory);

        assertEquals(testCategory.getName(), categoryDTO.getName());
        assertEquals(testCategory.getId(), categoryDTO.getId());
    }
}