package com.blocki.springrestonlinestore.api.v1.model;

import com.blocki.springrestonlinestore.core.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotBlank
    @JsonProperty("user_seller")
    private UserDTO userDTO;

    @NotBlank
    @JsonProperty("category")
    private CategoryDTO categoryDTO;

    @JsonProperty("shopping_cart_items")
    private Set<ShoppingCartItemDTO> shoppingCartItemDTOs = new HashSet<>();

    @NotBlank
    @Size(min = 1, max = 32)
    private  String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonProperty("product_status")
    private ProductStatus productStatus;

    @JsonProperty("creation_date")
    @NotBlank
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;

    @Nullable
    private String description;

    @Positive
    @NumberFormat(pattern = "#.##")
    @NotBlank
    private BigDecimal cost;

    @Nullable
    private Byte[] photo;

    @NotBlank
    @JsonProperty("product_url")
    private String productUrl;
}
