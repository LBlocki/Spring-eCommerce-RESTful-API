package com.blocki.springecommerceapp.core.repositories;

import com.blocki.springecommerceapp.core.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
