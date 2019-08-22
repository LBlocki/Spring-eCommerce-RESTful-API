package com.blocki.springrestonlinestore.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "shopping_carts")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ShoppingCart extends BaseEntity {

    public enum CartStatus { ACTIVE, COMPLETED, ERROR }

    @Builder// Otherwise Builder will ignore the initializing expression for Set...
    public ShoppingCart(Long id, User user, List<ShoppingCartItem> shoppingCartItems, LocalDate creationDate, CartStatus cartStatus) {

        super(id);
        this.user = user;
        this.shoppingCartItems = Optional.ofNullable(shoppingCartItems).orElse(this.shoppingCartItems);
        this.creationDate = creationDate;
        this.cartStatus = cartStatus;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "cart_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;
}
