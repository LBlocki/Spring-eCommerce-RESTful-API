package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CategoryMapper {

    abstract public CategoryDTO categoryToCategoryDTO(Category category);

    abstract public Category categoryDTOtoCategory(CategoryDTO categoryDTO);
}
