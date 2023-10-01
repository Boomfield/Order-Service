package com.example.ordersservice.controllers;

import com.example.ordersservice.models.Order;
import com.example.ordersservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    // Extract interface in future for DI
    private final OrderService orderService;
    private final RestTemplate restTemplate;

    // Use config file and spring annotation in future
    private String orderDomain = "http://localhost:8083";

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        Order addedOrder = orderService.addOrder(order);
        restTemplate.postForEntity(orderDomain + "/barista/receive-order", order, ResponseEntity.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedOrder);
    }

    @PostMapping("/cancel/{orderNumber}")
    public ResponseEntity<String> cancelOrder(@PathVariable int orderNumber) {
        ResponseEntity<Boolean> response = restTemplate.postForEntity(orderDomain + "/barista/cancel-order/" + orderNumber, HttpEntity.EMPTY, Boolean.class);

        if (response.getBody()) {
            orderService.deleteOrder(orderNumber);
            return ResponseEntity.ok("Order canceled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cannot be canceled.");
        }
    }

    @PostMapping("/pickup/{orderNumber}")
    public ResponseEntity<Void> pickUpOrder(@PathVariable int orderNumber) {
        orderService.processCompletedOrders(orderNumber);
        return ResponseEntity.ok().build();
    }
}

