package com.blocki.springrestonlinestore.core.repositories;

import com.blocki.springrestonlinestore.core.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
