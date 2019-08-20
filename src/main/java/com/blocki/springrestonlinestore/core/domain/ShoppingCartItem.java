package com.blocki.springrestonlinestore.core.domain;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Shopping_Cart_Items")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ShoppingCartItem extends  BaseEntity {

    @Builder
    public ShoppingCartItem(Long id, Product product, ShoppingCart shoppingCart, int quantity, BigDecimal totalCost) {
        super(id);
        this.product = product;
        this.shoppingCart = shoppingCart;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;
}
