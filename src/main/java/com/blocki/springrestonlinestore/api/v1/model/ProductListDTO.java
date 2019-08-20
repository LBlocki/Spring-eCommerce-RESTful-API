package com.blocki.springrestonlinestore.api.v1.model;

import com.blocki.springrestonlinestore.core.domain.Product;
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
    private List<Product> productDTOs = new ArrayList<>();
}
