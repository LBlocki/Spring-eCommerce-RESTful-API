package com.blocki.springrestonlinestore.core.repositories;

import com.blocki.springrestonlinestore.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findAllByName(String name);
}
