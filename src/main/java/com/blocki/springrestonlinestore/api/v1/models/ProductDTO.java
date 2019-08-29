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

    private Long id;

    @NotNull(message = "field value cannot be null")
    @JsonProperty("user_seller")
    private UserDTO userDTO;

    @NotNull(message = "field value cannot be null")
    @JsonProperty("category")
    private CategoryDTO categoryDTO;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 32, message = "field's value must have between 1 and 32 characters")
    private String name;

    @NotNull(message = "field value cannot be null")
    @JsonProperty("product_status")
    private Product.ProductStatus productStatus;

    @JsonProperty("creation_date")
    @NotNull(message = "field value cannot be null")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate creationDate;

    @Nullable
    private String description;

    @Positive(message = "field's value must be greater than 0")
    @NumberFormat(pattern = "#.##")
    @NotNull(message = "field value cannot be null")
    private BigDecimal cost;

    @Nullable
    private Byte[] photo;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @JsonProperty("product_url")
    private String productUrl;
}
