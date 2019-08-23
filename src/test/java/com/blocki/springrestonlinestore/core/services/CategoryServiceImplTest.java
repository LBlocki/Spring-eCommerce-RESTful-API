package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.CategoryMapper;
import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.api.v1.models.CategoryListDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    private static final Long ID = 2L;
    private static final String name = "Clothes";

    @InjectMocks
    private CategoryServiceImpl categoryInterfaceImpl;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllCategories() {

        //given
        List<Category> categories = Arrays.asList(new Category(), new Category());
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        //when
        CategoryListDTO categoryListDTO = categoryInterfaceImpl.getAllCategories();

        //then
        assertNotNull(categoryListDTO);

        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getCategoryById() {

        //given
        Category category = Category.builder().id(ID).name(name).build();
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));

        //when
        CategoryDTO categoryDTO = categoryInterfaceImpl.getCategoryById(ID);

        //than
        assertNotNull(categoryDTO);
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());

        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }
}