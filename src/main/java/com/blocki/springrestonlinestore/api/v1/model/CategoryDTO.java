package com.blocki.springrestonlinestore.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @NotBlank
    private Long id;

    private Set<ProductDTO> productsDTO = new HashSet<>();

    @NotBlank
    @Size(min = 1, max = 32)
    private String name;
}
