package com.blocki.springrestonlinestore.core.config.resourceAssemblers;

import com.blocki.springrestonlinestore.api.v1.controllers.CategoryController;
import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CategoryResourceAssembler implements ResourceAssembler<CategoryDTO, Resource<CategoryDTO>> {

    @Override
    public Resource<CategoryDTO> toResource(CategoryDTO categoryDTO) {

        return new Resource<>(categoryDTO,
                linkTo(methodOn(CategoryController.class).getCategoryById(categoryDTO.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("categories"));
    }
}
