package com.blocki.springrestonlinestore.api.v1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Relation(value = "category", collectionRelation = "categories")
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Name field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 32, message = "Name field value must have between 1 and 32 characters")
    private String name;
}
