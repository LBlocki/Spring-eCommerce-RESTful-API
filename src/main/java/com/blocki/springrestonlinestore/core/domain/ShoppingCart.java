package com.blocki.springrestonlinestore.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "shopping_carts")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ShoppingCart extends BaseEntity {

    public enum CartStatus { ACTIVE, COMPLETED, ERROR }

    @Builder// Otherwise Builder will ignore the initializing expression for Set...
    public ShoppingCart(Long id, User user, Set<ShoppingCartItem> shoppingCartItems, LocalDate creationDate, CartStatus cartStatus) {

        super(id);
        this.user = user;
        this.shoppingCartItems = Optional.ofNullable(shoppingCartItems).orElse(this.shoppingCartItems);
        this.creationDate = creationDate;
        this.cartStatus = cartStatus;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private Set<ShoppingCartItem> shoppingCartItems = new HashSet<>();

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "cart_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;
}
