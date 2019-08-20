package com.blocki.springrestonlinestore.core.domain;

import com.blocki.springrestonlinestore.core.enums.ProductStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Builder// Otherwise Builder will ignore the initializing expression for Set...
    public Product(Long id, User user, Category category, Set<ShoppingCartItem> shoppingCartItems,
                   String name, ProductStatus productStatus, LocalDate creationDate, String description, BigDecimal cost, Byte[] photo) {

        super(id);
        this.user = user;
        this.category = category;
        this.shoppingCartItems = Optional.ofNullable(shoppingCartItems).orElse(this.shoppingCartItems);
        this.name = name;
        this.productStatus = productStatus;
        this.creationDate = creationDate;
        this.description = description;
        this.cost = cost;
        this.photo = photo;
    }

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<ShoppingCartItem> shoppingCartItems  = new HashSet<>();

    @Column(nullable = false)
    private String name;

    @Column(name = "product_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    private String description;

    @Column(nullable = false)
    private BigDecimal cost;

    private Byte[] photo;     //todo refactor photo for holding list of paths to the actual images instead of storing them in database
}
