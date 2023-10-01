package com.example.ordersservice.models;

import com.example.ordersservice.models.enums.CoffeeSize;
import com.example.ordersservice.models.enums.CoffeeType;
import com.example.ordersservice.models.enums.MilkType;
import com.example.ordersservice.models.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "coffee_type")
    private CoffeeType coffeeType;
    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private CoffeeSize size;
    @Enumerated(EnumType.STRING)
    @Column(name = "milk_type")
    private MilkType milkType;
    private boolean onSite;
    private double price;
    private int orderNumber;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
