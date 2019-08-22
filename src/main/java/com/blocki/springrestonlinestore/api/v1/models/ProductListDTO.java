package com.blocki.springrestonlinestore.api.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDTO {

    @JsonProperty("products")
    private List<ProductDTO> productDTOs = new ArrayList<>();
}
