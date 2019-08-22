package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.api.v1.models.CategoryListDTO;

public interface CategoryInterface {

    CategoryListDTO getAllCategories();

    CategoryDTO getCategoryById(Long id);
}
