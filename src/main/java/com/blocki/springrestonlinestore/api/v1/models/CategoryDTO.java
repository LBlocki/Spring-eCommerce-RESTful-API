package com.blocki.springrestonlinestore.api.v1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @NotNull(message = "field value cannot be null")
    private Long id;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 32, message = "field's value must have between 1 and 32 characters")
    private String name;
}
