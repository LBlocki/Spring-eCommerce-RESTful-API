package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.api.v1.models.CategoryListDTO;
import com.blocki.springrestonlinestore.core.services.CategoryService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

public class CategoryControllerTest {

    private final static Long ID = 2L;
    private final static String NAME = "Clothes";

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryDTO categoryDTO = new CategoryDTO();
    private List<CategoryDTO> categoryDTOArrayList = new ArrayList<>(Arrays.asList(categoryDTO, categoryDTO));


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        categoryDTO.setId(ID);  //todo refactor after implementing builder
        categoryDTO.setName(NAME);
    }

    @Test
    public void getAllCategories() throws Exception{

        //given
        CategoryListDTO categoryListDTO = new CategoryListDTO(categoryDTOArrayList);
        Mockito.when(categoryService.getAllCategories()).thenReturn(categoryListDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders
                .get(CategoryController.CATEGORIES_BASIC_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories", Matchers.hasSize(categoryDTOArrayList.size())))
                .andDo(document(CategoryController.CATEGORIES_BASIC_URL));

        Mockito.verify(categoryService, Mockito.times(1)).getAllCategories();
    }

    @Test
    public void getCategoryById() throws Exception{

        //given
        Mockito.when(categoryService.getCategoryById(Mockito.anyLong())).thenReturn(categoryDTO);

        //than
        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.CATEGORIES_BASIC_URL + "/" + categoryDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(categoryDTO.getId().intValue())))
                .andDo(document(CategoryController.CATEGORIES_BASIC_URL + "/" + categoryDTO.getId()));

        Mockito.verify(categoryService, Mockito.times(1)).getCategoryById(Mockito.anyLong());
    }
}