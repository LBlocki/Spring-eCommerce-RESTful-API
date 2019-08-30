package com.blocki.springrestonlinestore.core.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    public enum Gender {MALE, FEMALE}

    public User(Long id, List<Product> products, ShoppingCart shoppingCart, String firstName, String lastName,
                String address, String country, String phoneNumber, LocalDate creationDate, String emailAddress,
                String username, char[] password, Gender gender) {

        super(id);
        this.products = Optional.ofNullable(products).orElse(this.products);
        this.shoppingCart = shoppingCart;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private ShoppingCart shoppingCart;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String country;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(nullable = false, unique = true)
    private String username;

    private char[] password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

}