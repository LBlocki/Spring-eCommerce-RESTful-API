package com.blocki.springrestonlinestore.api.v1.models;

import com.blocki.springrestonlinestore.core.config.parsers.LocalDateDeserializer;
import com.blocki.springrestonlinestore.core.config.parsers.LocalDateSerializer;
import com.blocki.springrestonlinestore.core.config.validators.ExtendedEmailValidator;
import com.blocki.springrestonlinestore.core.config.validators.PhoneNumberValidator;
import com.blocki.springrestonlinestore.core.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(value = "user", collectionRelation = "users")
public class UserDTO {

    @NotNull(message = "field value cannot be null")
    private Long id;

    @JsonProperty("products")
    private List<ProductDTO> productDTOs = new ArrayList<>();

    @JsonProperty("shopping_cart")
    private ShoppingCartDTO shoppingCartDTO;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 32, message = "field's value must have between 1 and 32 characters")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 32, message = "field's value must have between 1 and 32 characters")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 64, message = "field's value must have between 1 and 64 characters")
    private String address;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 16, message = "field's value must have between 1 and 16 characters")
    private String country;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 12, message = "field's value must have between 1 and 12 characters")
    @JsonProperty("phone_number")
    @PhoneNumberValidator
    private String phoneNumber;

    @JsonProperty("creation_date")
    @NotNull(message = "field value cannot be null")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate creationDate;

    @Email
    @ExtendedEmailValidator
    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 32, message = "field's value must have between 4 and 32 characters")
    private String emailAddress;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @Size(min = 1, max = 16, message = "field's value must have between 1 and 16 characters")
    private String username;

    @Size(min = 6, max = 32, message = "field's value must have between 6 and 32 characters")
    @NotNull(message = "field value cannot be null")
    private char[] password;

    @NotNull(message = "field value cannot be null")
    private User.Gender gender;

    @NotBlank(message = "field value cannot be null or contain only blank characters")
    @JsonProperty("user_url")
    private String userUrl;
}
