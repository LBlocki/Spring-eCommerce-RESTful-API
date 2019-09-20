package com.blocki.springecommerceapp.core.services;

import com.blocki.springecommerceapp.api.v1.models.CategoryDTO;
import com.blocki.springecommerceapp.core.bootstrap.TestEntityGenerator;
import com.blocki.springecommerceapp.core.config.resourceAssemblers.CategoryResourceAssembler;
import com.blocki.springecommerceapp.core.domain.Category;
import com.blocki.springecommerceapp.core.repositories.CategoryRepository;
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

    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();

    private Category category;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Spy
    private CategoryResourceAssembler categoryResourceAssembler = new CategoryResourceAssembler();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        category = testEntityGenerator.generateCategory();
    }

    @Test
    public void getAllCategories() {

        //given
        Mockito.when(categoryRepository.findAll()).thenReturn(Arrays.asList(category, category));

        //when
        Resources<Resource<CategoryDTO>> categories = categoryService.getAllCategories();

        //then
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
        Resource<CategoryDTO> TestCategoryDTO = categoryService.getCategoryById(category.getId());

        //then
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(categoryResourceAssembler, Mockito.times(1)).toResource(Mockito.any(CategoryDTO.class));

        Mockito.verifyNoMoreInteractions(categoryRepository);
        Mockito.verifyNoMoreInteractions(categoryResourceAssembler);

        assertNotNull(TestCategoryDTO);

        assertEquals(TestCategoryDTO.getContent().getId(), category.getId());
        assertEquals(TestCategoryDTO.getContent().getName(), category.getName());
    }
}