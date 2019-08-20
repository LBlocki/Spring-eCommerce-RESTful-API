package com.blocki.springrestonlinestore.core.domain;

import com.blocki.springrestonlinestore.core.enums.Gender;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
public class User extends BaseEntity {

    @Builder
    public User(Long id, Set<Product> products, Set<ShoppingCart> shoppingCarts,
                String firstName, String lastName, String address, String country,
                String phoneNumber, LocalDate creationDate, String emailAddress, String password, Gender gender) {

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

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;  //todo Address should be refactored in the future : perhaps another table to allow control and validation for DTO

    @Column(name = "country", nullable = false)
    private String country; //todo Country should be refactored in the future : perhaps another table to allow control and validation for DTO

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; //todo phoneNumber requires validation. Libphonenumber with Hibernate Validator seems like a good choice for DTO

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;    //todo Password needs refactoring for validation purposes for DTO

    @Column(name = "gender", nullable = false)
    private Gender gender;


}