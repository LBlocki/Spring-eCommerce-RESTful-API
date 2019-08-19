package com.blocki.springrestonlinestore.core.domain;

import com.blocki.springrestonlinestore.core.enums.CartStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "shopping_carts")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
class ShoppingCart extends BaseEntity {

    // Otherwise Builder will ignore the initializing expression for Set...
    @Builder
    public ShoppingCart(Long id, @NotBlank User user, Set<ShoppingCartItem> shoppingCartItems, @NotBlank LocalDate creationDate, @NonNull CartStatus cartStatus) {

        super(id);
        this.user = user;
        this.shoppingCartItems = Optional.ofNullable(shoppingCartItems).orElse(this.shoppingCartItems);
        this.creationDate = creationDate;
        this.cartStatus = cartStatus;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotBlank
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private Set<ShoppingCartItem> shoppingCartItems = new HashSet<>();

    @CreationTimestamp
    @NotBlank
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Column(name = "cart_status")
    private CartStatus cartStatus;
}
