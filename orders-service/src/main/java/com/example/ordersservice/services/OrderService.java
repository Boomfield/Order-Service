package com.example.ordersservice.services;

import com.example.ordersservice.models.Order;
import com.example.ordersservice.models.enums.OrderStatus;
import com.example.ordersservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order addOrder(Order order) {
        order.setOrderNumber(generateOrderNumber());
        order.setOrderStatus(OrderStatus.WAITING);
        orderRepository.save(order);

        return order;
    }

    public boolean deleteOrder(int orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);

        if (order != null) {
            orderRepository.delete(order);
            return true;
        }
        return false;
    }

    public void processCompletedOrders(int orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        order.setOrderStatus(OrderStatus.PICKED_UP);
        orderRepository.save(order);
    }

    // Fix possible duplicated numbers
    private int generateOrderNumber() {
        return (int) (Math.random() * 10000);
    }
}

