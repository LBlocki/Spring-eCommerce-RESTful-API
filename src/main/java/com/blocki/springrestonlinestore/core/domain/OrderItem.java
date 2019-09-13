package com.blocki.springrestonlinestore.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Order_items")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderItem extends  BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;
}
