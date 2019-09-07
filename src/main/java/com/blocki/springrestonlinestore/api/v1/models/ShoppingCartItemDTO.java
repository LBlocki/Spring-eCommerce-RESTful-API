package com.blocki.springrestonlinestore.api.v1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.core.Relation;

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
    @NotNull(message = "Product field cannot be null")
    @ToString.Exclude
    private ProductDTO productDTO;

    @JsonProperty("shopping_cart")
    @NotNull(message = "Shopping Cart field value cannot be null")
    @JsonBackReference
    @ToString.Exclude
    private ShoppingCartDTO shoppingCartDTO;

    @NotNull(message = "Shopping Cart id field cannot be null")
    @JsonProperty("shopping_cart_id")
    private Long ShoppingCartDTOId;

    @NotNull(message = "Quantity value cannot be null")
    @Positive(message = "field must have positive value")
    private Integer quantity;

    @Positive(message = "Total cost field value must be greater than 0")
    @NotNull(message = "Total cost field value cannot be null")
    @JsonProperty("total_cost")
    @NumberFormat(pattern = "#.##")
    private BigDecimal totalCost;

}
