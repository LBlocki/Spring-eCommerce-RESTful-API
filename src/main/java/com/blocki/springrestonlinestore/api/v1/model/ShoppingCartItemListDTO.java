package com.blocki.springrestonlinestore.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemListDTO {

    @JsonProperty("shopping_cart_items")
    private List<ShoppingCartItemDTO> shoppingCartItemDTOs = new ArrayList<>();
}
