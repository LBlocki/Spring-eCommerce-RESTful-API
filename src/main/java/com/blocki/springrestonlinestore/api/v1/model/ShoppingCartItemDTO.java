package com.blocki.springrestonlinestore.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemDTO {

    @NotBlank
    private Long id;

    @NotBlank
    @JsonProperty("product")
    private ProductDTO productDTO;

    @NotBlank
    @JsonProperty("shopping_cart")
    private ShoppingCartDTO shoppingCartDTO;

    @NotBlank
    @Positive
    private Integer quantity;

    @Positive
    @NotBlank
    @JsonProperty("total_cost")
    @NumberFormat(pattern = "#.##")
    private BigDecimal totalCost;

    @NotBlank
    @JsonProperty("shopping_cart_item_url")
    private String shoppingCartItemUrl;
}
