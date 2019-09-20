package com.blocki.springecommerceapp.core.repositories;

import com.blocki.springecommerceapp.core.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}
