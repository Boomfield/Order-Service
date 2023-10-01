package com.example.baristaservice.controllers;

import com.example.baristaservice.models.Order;
import com.example.baristaservice.services.OrderProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/barista")
@RequiredArgsConstructor
public class BaristaController {

    // Use config file and spring annotation in future
    private String orderDomain = "http://localhost:8082";
    private final RestTemplate restTemplate;

    // Extract interface in future for DI
    private final OrderProcessingService orderProcessingService;


    @PostMapping("/process")
    public ResponseEntity<Void> processNextOrder() {
        orderProcessingService.processNextOrder();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel-order/{orderNumber}")
    public ResponseEntity<Boolean> cancelOrder(@PathVariable int orderNumber) {
        boolean canceled = orderProcessingService.cancelOrder(orderNumber);

        return ResponseEntity.ok(canceled);
    }

    @PostMapping("/receive-order")
    public ResponseEntity<String> receiveOrder(@RequestBody Order order) {
        orderProcessingService.addOrder(order);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/pickup/{orderNumber}")
    public ResponseEntity<String> pickUpOrder(@PathVariable int orderNumber) {
        boolean pickUp = orderProcessingService.pickUpOrder(orderNumber);

        if (pickUp) {
            restTemplate.postForEntity(orderDomain + "/orders/pickup/" + orderNumber, HttpEntity.EMPTY, ResponseEntity.class);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order in process");
    }

}
