package com.blocki.springrestonlinestore.api.v1.mappers;

import com.blocki.springrestonlinestore.api.v1.models.CategoryDTO;
import com.blocki.springrestonlinestore.core.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CategoryMapper {

    abstract public CategoryDTO categoryToCategoryDTO(Category category);

    abstract public Category categoryDTOtoCategory(CategoryDTO categoryDTO);

    @AfterMapping
    void setAdditionalCategoryDTOParameters(Category category, @MappingTarget CategoryDTO categoryDTO) {

        if(log.isDebugEnabled()) {

            log.debug(CategoryMapper.class.getName() + ":(categoryToCategoryDTO): category:\n"
                    + category.toString() + ",\n categoryDTO:" + categoryDTO.toString() + "\n");
        }
    }

    @AfterMapping
    void setAdditionalCategoryParameters(CategoryDTO categoryDTO, @MappingTarget Category category) {

        if(log.isDebugEnabled()) {

            log.debug(CategoryMapper.class.getName() + ":(categoryDTOtoCategory): categoryDTO:\n" +
                    categoryDTO.toString() + ",\n category:" + category.toString() + "\n");
        }
    }
}
