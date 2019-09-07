package com.blocki.springrestonlinestore.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Shopping_Cart_Items")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ShoppingCartItem extends  BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    @ToString.Exclude
    private ShoppingCart shoppingCart;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;
}
