package com.blocki.springrestonlinestore.core.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "ShoppingCartItems")
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

    @NotBlank
    @Size(min = 1, max = 1000)
    @Column(name = "quantity")
    private int quantity;

    @NotBlank
    @Column(name = "total_cost")
    private BigDecimal totalCost;
}
