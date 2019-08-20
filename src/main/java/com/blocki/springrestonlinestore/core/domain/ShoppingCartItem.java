package com.blocki.springrestonlinestore.core.domain;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Shopping_Cart_Items")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartItem extends  BaseEntity {

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
