package com.github.renny.order.system.modules.orderitem.repository;

import com.github.renny.order.system.modules.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrderNum(String orderNum);
}
