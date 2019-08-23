package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.mappers.CategoryMapper;
import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.api.v1.models.CategoryListDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryListDTO getAllCategories() {

        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        for(Category category : categoryRepository.findAll()) {

            CategoryDTO newCategoryDTO = categoryConverter.categoryToCategoryDTO(category);
            categoryDTOs.add(newCategoryDTO);
        }

        return new CategoryListDTO(categoryDTOs);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {

        return categoryRepository
                .findById(id)
                .map(categoryConverter::categoryToCategoryDTO)
                .orElseThrow(RuntimeException::new);    //todo custom exception
    }
}
