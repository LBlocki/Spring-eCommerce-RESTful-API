package com.blocki.springrestonlinestore.core.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    public enum Gender {MALE, FEMALE}

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Product> products = new ArrayList<>();

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;

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