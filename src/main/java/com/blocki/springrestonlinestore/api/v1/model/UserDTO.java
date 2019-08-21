package com.blocki.springrestonlinestore.api.v1.model;

import com.blocki.springrestonlinestore.core.domain.User;
import com.blocki.springrestonlinestore.core.validators.ExtendedEmailValidator;
import com.blocki.springrestonlinestore.core.validators.PhoneNumberValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank
    private Long id;

    @JsonProperty("products")
    private Set<ProductDTO> productDTOs = new HashSet<>();

    @JsonProperty("shopping_carts")
    private Set<ShoppingCartDTO> shoppingCartDTOs = new HashSet<>();

    @NotBlank
    @Size(min = 1, max = 32)
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 32)
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank
    @Size(min = 1, max = 64)
    private String address;

    @NotBlank
    @Size(min = 1, max = 16)
    private String country;

    @NotBlank
    @Size(min = 1, max = 12)
    @JsonProperty("phone_number")
    @PhoneNumberValidator
    private String phoneNumber;

    @NotBlank
    @JsonProperty("creation_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;

    @Email
    @ExtendedEmailValidator
    @NotBlank
    @Size(min = 4, max = 32)
    private String emailAddress;

    @NotBlank
    @Size(min = 1, max = 16)
    private String username;

    @NotBlank
    @Size(min = 6, max = 32)
    private char[] password;

    @NotNull
    private User.Gender gender;

    @NotBlank
    @JsonProperty("user_url")
    private String userUrl;
}
