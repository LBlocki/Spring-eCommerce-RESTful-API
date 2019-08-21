package com.blocki.springrestonlinestore.core.repositories;

import com.blocki.springrestonlinestore.core.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {


}
