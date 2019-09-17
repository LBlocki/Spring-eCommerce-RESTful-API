package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.core.bootstrap.TestEntityGenerator;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.CategoryResourceAssembler;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import com.blocki.springrestonlinestore.core.services.CategoryService;
import com.blocki.springrestonlinestore.core.services.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CategoryControllerTest {

    private static final String CATEGORIES_BASIC_URL = "/api/v1/categories";

    private final TestEntityGenerator testEntityGenerator = new TestEntityGenerator();
    private final Category testCategory = testEntityGenerator.generateCategory();

    private MockMvc mockMvc;

    private RelProvider relProvider = new RelProvider() {
        @Override
        public String getItemResourceRelFor(Class<?> aClass) {

            return "category";
        }

        @Override
        public String getCollectionResourceRelFor(Class<?> aClass) {
            return "categories";
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return true;
        }
    };

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private CategoryResourceAssembler categoryResourceAssembler = new CategoryResourceAssembler();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        CategoryService categoryService = new CategoryServiceImpl(categoryRepository, categoryResourceAssembler);
        CategoryController categoryController = new CategoryController(categoryService);

        TypeConstrainedMappingJackson2HttpMessageConverter messageConverter =
                new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        messageConverter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON,MediaTypes.HAL_JSON_UTF8));

        ObjectMapper objectMapper = messageConverter.getObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.setHandlerInstantiator(
                new Jackson2HalModule.HalHandlerInstantiator(relProvider, null, null));

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setMessageConverters(messageConverter).build();
    }

    @Test
    public void getAllCategories() throws Exception {

        Mockito.when(categoryRepository.findAll()).thenReturn(Arrays.asList(testCategory, testCategory));

        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORIES_BASIC_URL).accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._embedded.categories[0].id",
                        is(testCategory.getId().intValue())))
                .andExpect(jsonPath("$._embedded.categories[0].name",
                        is(testCategory.getName())))
                .andExpect(jsonPath("$._embedded.categories[0]._links.self.href",
                        is("http://localhost/api/v1/categories/" + testCategory.getId().intValue())))
                .andExpect(jsonPath("$._embedded.categories[0]._links.get_list_of_categories.href",
                        is("http://localhost/api/v1/categories")))
                .andExpect(jsonPath("$._embedded.categories[1].id",
                        is(testCategory.getId().intValue())))
                .andExpect(jsonPath("$._embedded.categories[1].name",
                        is(testCategory.getName())))
                .andExpect(jsonPath("$._embedded.categories[1]._links.self.href",
                        is("http://localhost/api/v1/categories/" + testCategory.getId().intValue())))
                .andExpect(jsonPath("$._embedded.categories[1]._links.get_list_of_categories.href",
                        is("http://localhost/api/v1/categories")))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/api/v1/categories")));

        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void getCategoryById() throws Exception {

        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testCategory));

        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORIES_BASIC_URL + "/" + testCategory.getId())
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id",
                        is(testCategory.getId().intValue())))
                .andExpect(jsonPath("$.name",
                        is(testCategory.getName())))
                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/api/v1/categories/" + testCategory.getId().intValue())))
                .andExpect(jsonPath("$._links.get_list_of_categories.href",
                        is("http://localhost/api/v1/categories")));

        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(categoryRepository);
    }
}