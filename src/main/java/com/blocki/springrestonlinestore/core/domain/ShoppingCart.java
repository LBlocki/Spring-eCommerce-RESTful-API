package com.blocki.springrestonlinestore.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ShoppingCart extends BaseEntity {

    public enum CartStatus { ACTIVE, COMPLETED, ERROR }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "cart_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;
}
