package com.github.renny.order.system.modules.order.repository;


import com.github.renny.order.system.modules.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);

    Optional<Order> findByOrderNum(String orderNum);

    Optional<Order> findByOrderNumAndUserId(String orderNum, Long userId);

}
