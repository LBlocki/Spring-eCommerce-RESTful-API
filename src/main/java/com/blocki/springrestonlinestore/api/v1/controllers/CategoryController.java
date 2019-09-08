package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.core.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(CategoryController.CATEGORIES_BASIC_URL)
public class CategoryController {

    public final static String CATEGORIES_BASIC_URL = "/api/v1/categories";

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {

        if(log.isDebugEnabled()) {

            log.debug(CategoryController.class.getName() + ":(constructor):Injected category service:\n"
                    + categoryService.toString() + "\n");
        }

        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resources<Resource<CategoryDTO>> getAllCategories() {

        if(log.isDebugEnabled()) {

            log.debug(CategoryController.class.getName() + ":(getAllCategories):Running method\n");
        }

        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<CategoryDTO> getCategoryById(@PathVariable Long id) {

        if(log.isDebugEnabled()) {

            log.debug(CategoryController.class.getName() + ":(getCategoryById): ID value in path: " + id  + "\n");
        }

        return categoryService.getCategoryById(id);
    }
}
