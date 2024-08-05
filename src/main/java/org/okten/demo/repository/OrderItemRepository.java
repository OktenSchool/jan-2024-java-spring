package org.okten.demo.repository;

import org.okten.demo.entity.OrderItem;
import org.okten.demo.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
