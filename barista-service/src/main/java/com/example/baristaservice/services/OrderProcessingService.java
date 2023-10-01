package com.example.baristaservice.services;

import com.example.baristaservice.models.Order;
import com.example.baristaservice.models.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
public class OrderProcessingService {
    private ConcurrentLinkedQueue<Order> orders = new ConcurrentLinkedQueue<>();

    public void processNextOrder() {

        Order order = nextOrder();

        if (order == null) {
            throw new NullPointerException("Order not found with ID: " + order.getOrderNumber());
        }

        if (order.getOrderStatus() == OrderStatus.WAITING) {
            order.setOrderStatus(OrderStatus.IN_PREPARATION);
            orders.add(order);
            try {
                Thread.sleep(70000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            order.setOrderStatus(OrderStatus.FINISHED);
        } else {
            throw new IllegalArgumentException("Order with ID " + order.getOrderNumber() + " is already being processed.");
        }
    }

    public void deleteOrder(int orderNumber) {
        orders.removeIf(x -> x.getOrderNumber() == orderNumber);
    }

    public boolean pickUpOrder(int orderNumber) {
        Order order = orders.stream().filter(x -> x.getOrderNumber() == orderNumber).findFirst().get();
        if (order.getOrderStatus() == OrderStatus.FINISHED) {
            deleteOrder(orderNumber);
            return true;
        }
        return false;
    }

    public void addOrder(Order order) {
        orders.offer(order);
    }

    public boolean cancelOrder(int orderNumber) {
        Order order = orders.stream().filter(x -> x.getOrderNumber() == orderNumber).findFirst().get();
        if (order.getOrderStatus() == OrderStatus.WAITING) {
            deleteOrder(orderNumber);
            return true;
        }
        return false;
    }

    private Order nextOrder() {
        return orders.poll();
    }

}
