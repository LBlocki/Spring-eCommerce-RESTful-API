package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.api.v1.models.CategoryListDTO;

public interface CategoryService {

    CategoryListDTO getAllCategories();

    CategoryDTO getCategoryById(Long id);
}