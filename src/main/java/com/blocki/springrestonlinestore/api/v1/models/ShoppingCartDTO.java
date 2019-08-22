package com.blocki.springrestonlinestore.api.v1.models;

import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private Set<ShoppingCartItemDTO> shoppingCartItemDTOs = new HashSet<>();

    @NotNull
    @JsonProperty("creation_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;

    @JsonProperty("cart_status")
    @NotNull
    private ShoppingCart.CartStatus cartStatus;

    @NotBlank
    @JsonProperty("shopping_cart_url")
    private String shoppingCartUrl;

}
