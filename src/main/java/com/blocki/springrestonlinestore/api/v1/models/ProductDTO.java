package com.blocki.springrestonlinestore.api.v1.models;

import com.blocki.springrestonlinestore.core.config.parsers.LocalDateDeserializer;
import com.blocki.springrestonlinestore.core.config.parsers.LocalDateSerializer;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @NotNull
    private Long id;

    @NotNull
    @JsonProperty("user_seller")
    private UserDTO userDTO;

    @NotNull
    @JsonProperty("category")
    private CategoryDTO categoryDTO;

    @NotBlank
    @Size(min = 1, max = 32)
    private  String name;

    @NotNull
    @JsonProperty("product_status")
    private Product.ProductStatus productStatus;

    @JsonProperty("creation_date")
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate creationDate;

    @Nullable
    private String description;

    @Positive
    @NumberFormat(pattern = "#.##")
    @NotNull
    private BigDecimal cost;

    @Nullable
    private Byte[] photo;

    @NotBlank
    @JsonProperty("product_url")
    private String productUrl;
}
