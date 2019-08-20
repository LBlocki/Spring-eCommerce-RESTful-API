package com.blocki.springrestonlinestore.api.v1.mapper;

import com.blocki.springrestonlinestore.api.v1.model.CategoryDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "products", target = "productsDTO")
    CategoryDTO categoryToCategoryDTO(Category category);

    @Mapping(source = "productsDTO", target = "products")
    Category categoryDTOtoCategory(CategoryDTO categoryDTO);
}
