package com.blocki.springrestonlinestore.core.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

    @Builder// Otherwise Builder will ignore the initializing expression for Set...
    public Category(Long id, String name) {

        super(id);
        this.name = name;
    }

    @Column(unique = true, nullable = false)
    private String name;
}
