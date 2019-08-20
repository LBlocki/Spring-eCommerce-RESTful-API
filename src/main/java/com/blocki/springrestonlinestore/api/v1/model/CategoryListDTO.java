package com.blocki.springrestonlinestore.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDTO {

    @JsonProperty("categories")
    @NotBlank
    private List<CategoryDTO>  categoryDTOs = new ArrayList<>();
}
