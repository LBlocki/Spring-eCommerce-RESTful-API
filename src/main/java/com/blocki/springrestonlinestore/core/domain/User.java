package com.blocki.springrestonlinestore.core.domain;

import com.blocki.springrestonlinestore.core.enums.Gender;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
class User extends BaseEntity {

    @Builder// Otherwise Builder will ignore the initializing expression for Set...
    public User(Long id, Set<Product> products, Set<ShoppingCart> shoppingCarts, @NotBlank @Size(min = 2, max = 16) String firstName,
                @NotBlank @Size(min = 2, max = 16) String lastName, @NotBlank @Size(min = 2, max = 32) String address, @NotBlank @Size(max = 16) String country,
                @NotBlank @Size(min = 6, max = 12) String phoneNumber, @NotBlank LocalDate creationDate, @Email @NotBlank String emailAddress,
                @NotBlank @Size(min = 6, max = 32) String password, @NonNull Gender gender) {

        super(id);
        this.products = Optional.ofNullable(products).orElse(this.products);
        this.shoppingCarts = Optional.ofNullable(shoppingCarts).orElse(this.shoppingCarts);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.emailAddress = emailAddress;
        this.password = password;
        this.gender = gender;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<ShoppingCart> shoppingCarts = new HashSet<>();

    @NotBlank
    @Size(min = 2, max = 16)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 16)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 32)
    @Column(name = "address")
    private String address;  //todo Address should be refactored in the future : perhaps another table to allow control and validation

    @NotBlank
    @Size(max = 16)
    @Column(name = "country")
    private String country; //todo Country should be refactored in the future : perhaps another table to allow control and validation

    @NotBlank
    @Size(min = 6, max = 12)
    @Column(name = "phone_number")
    private String phoneNumber; //todo phoneNumber requires validation. Libphonenumber with Hibernate Validator seems like a good choice

    @CreationTimestamp
    @NotBlank
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Email
    @NotBlank
    @Column(name = "email_address")
    private String emailAddress;

    @NotBlank
    @Size(min = 6, max = 32)
    private String password;    //todo Password needs refactoring for validation purposes

    @NonNull
    @Enumerated(EnumType.STRING)
    private Gender gender;


}
