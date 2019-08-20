package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.CategoryDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOtoCategory(CategoryDTO categoryDTO);
}
