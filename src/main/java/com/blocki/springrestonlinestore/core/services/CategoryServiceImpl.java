package com.blocki.springrestonlinestore.core.services;

import com.blocki.springrestonlinestore.api.v1.controllers.CategoryController;
import com.blocki.springrestonlinestore.api.v1.mappers.CategoryMapper;
import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.core.config.resourceAssemblers.CategoryResourceAssembler;
import com.blocki.springrestonlinestore.core.exceptions.NotFoundException;
import com.blocki.springrestonlinestore.core.repositories.CategoryRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryResourceAssembler categoryResourceAssembler;
    private final CategoryMapper categoryConverter = Mappers.getMapper(CategoryMapper.class);

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryResourceAssembler categoryResourceAssembler) {
        this.categoryRepository = categoryRepository;
        this.categoryResourceAssembler = categoryResourceAssembler;
    }

    @Override
    public Resources<Resource<CategoryDTO>> getAllCategories() {

        List<Resource<CategoryDTO>> categories = categoryRepository
                .findAll()
                .stream()
                .map(categoryConverter::categoryToCategoryDTO)
                .map(categoryResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(categories,
                linkTo(methodOn(CategoryController.class).getAllCategories()).withSelfRel());
    }

    @Override
    public Resource<CategoryDTO> getCategoryById(Long id) {

        CategoryDTO categoryDTO =  categoryRepository
                                    .findById(id)
                                    .map(categoryConverter::categoryToCategoryDTO)
                                    .orElseThrow(NotFoundException::new);

        return categoryResourceAssembler.toResource(categoryDTO);
    }
}
