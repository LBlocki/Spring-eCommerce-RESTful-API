package com.blocki.springrestonlinestore.core.repositories;

import com.blocki.springrestonlinestore.core.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}
