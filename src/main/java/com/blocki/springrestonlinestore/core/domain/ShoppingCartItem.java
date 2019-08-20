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
class ShoppingCartItem extends  BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_cost")
    private BigDecimal totalCost;
}
