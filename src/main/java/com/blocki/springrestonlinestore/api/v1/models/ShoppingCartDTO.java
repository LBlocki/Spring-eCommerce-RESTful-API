package com.blocki.springrestonlinestore.api.v1.models;

import com.blocki.springrestonlinestore.core.config.parsers.LocalDateDeserializer;
import com.blocki.springrestonlinestore.core.config.parsers.LocalDateSerializer;
import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {

    @NotNull
    private Long id;

    @JsonProperty("user_id")
    @NotNull
    private UserDTO userDTO;

    @JsonProperty("shopping_cart_items")
    private List<ShoppingCartItemDTO> shoppingCartItemDTOs = new ArrayList<>();

    @NotNull
    @JsonProperty("creation_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate creationDate;

    @JsonProperty("cart_status")
    @NotNull
    private ShoppingCart.CartStatus cartStatus;

    @NotBlank
    @JsonProperty("shopping_cart_url")
    private String shoppingCartUrl;

}
