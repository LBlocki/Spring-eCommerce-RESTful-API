package com.blocki.springrestonlinestore.api.v1.controllers;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.core.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = CategoryController.CATEGORIES_BASIC_URL, produces = "application/hal+json")
public class CategoryController {

    final static String CATEGORIES_BASIC_URL = "/api/v1/categories";

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Resources<Resource<CategoryDTO>>> getAllCategories() {

        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<CategoryDTO>> getCategoryById(@PathVariable final Long id) {

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
