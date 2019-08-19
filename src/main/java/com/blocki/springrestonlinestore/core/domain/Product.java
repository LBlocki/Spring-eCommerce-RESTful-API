package com.blocki.springrestonlinestore.core.domain;

import com.blocki.springrestonlinestore.core.enums.ProductStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
class Product extends BaseEntity {

    @Builder// Otherwise Builder will ignore the initializing expression for Set...
    public Product(Long id, @NotBlank User user, @NotBlank Category category, Set<ShoppingCartItem> shoppingCartItems,
                   @NotBlank @Size(min = 1, max = 32) String name, @NonNull ProductStatus productStatus,
                   @NotBlank LocalDate creationDate, @Nullable String description, @NotBlank BigDecimal cost, @Nullable Byte[] photo) {

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
    @NotBlank
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotBlank
    Category category;

    @OneToMany(mappedBy = "product")
    private Set<ShoppingCartItem> shoppingCartItems  = new HashSet<>();

    @NotBlank
    @Size(min = 1, max = 32)
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Column(name = "product_status")
    private ProductStatus productStatus;

    @CreationTimestamp
    @NotBlank
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "description")
    @Nullable
    private String description;

    @Column(name = "cost")
    @NotBlank
    private BigDecimal cost;

    @Column(name = "photo")
    @Nullable
    private Byte[] photo;     //todo refactor photo for holding list of paths to the actual images instead of storing them in database
}
