package com.blocki.springrestonlinestore.core.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
class Category extends BaseEntity {

    @Builder// Otherwise Builder will ignore the initializing expression for Set...
    public Category(Long id, Set<Product> products,String name) {

        super(id);
        this.products = Optional.ofNullable(products).orElse(this.products);
        this.name = name;
    }

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    @Column(name = "name")
    private String name;
}
