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

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 1, max = 32)
    private String name;
}
