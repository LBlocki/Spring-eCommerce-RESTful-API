package com.blocki.springrestonlinestore.api.v1.models;

import com.blocki.springrestonlinestore.core.config.parsers.LocalDateDeserializer;
import com.blocki.springrestonlinestore.core.config.parsers.LocalDateSerializer;
import com.blocki.springrestonlinestore.core.domain.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.hateoas.core.Relation;
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
@Relation(value = "product", collectionRelation = "products")
public class ProductDTO {

    private Long id;

    @NotNull(message = "User field cannot be null")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDTO userDTO;

    @NotNull(message = "Owner_id field cannot be null")
    @JsonProperty("owner_id")
    private Long userDTOId;

    @NotNull(message = "Category field cannot be null")
    @JsonProperty("category")
    private CategoryDTO categoryDTO;

    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 1, max = 32, message = "Name field must be between 1 and 32 characters")
    private String name;

    @NotNull(message = "Product status field cannot be null")
    @JsonProperty("product_status")
    private Product.ProductStatus productStatus;

    @JsonProperty("creation_date")
    @NotNull(message = "Creation date cannot be null")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate creationDate;

    @Nullable
    private String description;

    @Positive(message = "Cost value must be positive")
    @NumberFormat(pattern = "#.##")
    @NotNull(message = "Cost field cannot be null")
    private BigDecimal cost;

    @Nullable
    private Byte[] photo;

}
