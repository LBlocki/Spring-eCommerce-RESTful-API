package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

public interface CategoryService {

    Resources<Resource<CategoryDTO>> getAllCategories();

    Resource<CategoryDTO> getCategoryById(Long id);
}
