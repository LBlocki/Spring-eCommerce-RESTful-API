package com.blocki.springrestonlinestore.api.v1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(value = "item", collectionRelation = "items")
public class ShoppingCartItemDTO {

    private Long id;

    @JsonProperty("product")
    @NotNull(message = "field value cannot be null")
    private ProductDTO productDTO;

    @JsonProperty("shopping_cart")
    @NotNull(message = "field value cannot be null")
    @JsonBackReference
    private ShoppingCartDTO shoppingCartDTO;

    @NotNull(message = "field value cannot be null")
    @Positive(message = "field must have positive value")
    private Integer quantity;

    @Positive(message = "field's value must be greater than 0")
    @NotNull(message = "field value cannot be null")
    @JsonProperty("total_cost")
    @NumberFormat(pattern = "#.##")
    private BigDecimal totalCost;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @JsonProperty("shopping_cart_item_url")
    private String shoppingCartItemUrl;
}
