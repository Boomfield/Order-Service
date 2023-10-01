package com.example.ordersservice.repositories;

import com.example.ordersservice.models.Order;
import com.example.ordersservice.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    Order findByOrderNumber(int orderNumber);
}
