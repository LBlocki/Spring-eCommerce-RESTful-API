package com.blocki.springrestonlinestore.api.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemDTO {

    @NotNull
    private Long id;

    @JsonProperty("product")
    @NotNull
    private ProductDTO productDTO;

    @JsonProperty("shopping_cart")
    @NotNull
    private ShoppingCartDTO shoppingCartDTO;

    @NotNull
    @Positive
    private Integer quantity;

    @Positive
    @NotNull
    @JsonProperty("total_cost")
    @NumberFormat(pattern = "#.##")
    private BigDecimal totalCost;

    @NotBlank
    @JsonProperty("shopping_cart_item_url")
    private String shoppingCartItemUrl;
}
