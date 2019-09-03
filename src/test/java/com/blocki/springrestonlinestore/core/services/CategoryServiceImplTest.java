package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.CategoryResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class CategoryServiceImplTest {

    private static final Long categoryID = 1L;
    private static final String categoryName = "Clothes";

    private Category category = new Category();

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Spy
    private CategoryResourceAssembler categoryResourceAssembler = new CategoryResourceAssembler();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        category.setId(categoryID);
        category.setName(categoryName);
    }

    @Test
    public void getAllCategories() {

        //given
        Mockito.when(categoryRepository.findAll()).thenReturn(Arrays.asList(category, category));

        //when
        Resources<Resource<CategoryDTO>> categories = categoryService.getAllCategories();

        //than
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(categoryRepository);

        assertNotNull(categories);
        assertThat(categories.getContent().size(), Matchers.equalTo(2));
    }

    @Test
    public void getCategoryById() {

        //given
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));

        //when
        Resource<CategoryDTO> categoryDTO = categoryService.getCategoryById(categoryID);

        //than
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(categoryResourceAssembler, Mockito.times(1)).toResource(Mockito.any(CategoryDTO.class));

        assertNotNull(categoryDTO);
        assertEquals(categoryDTO.getContent().getId(), category.getId());
        assertEquals(categoryDTO.getContent().getName(), category.getName());
    }
}