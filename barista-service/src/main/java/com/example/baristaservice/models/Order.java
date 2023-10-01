package com.example.baristaservice.models;

import com.example.baristaservice.models.enums.CoffeeSize;
import com.example.baristaservice.models.enums.CoffeeType;
import com.example.baristaservice.models.enums.MilkType;
import com.example.baristaservice.models.enums.OrderStatus;
import lombok.Data;

@Data
public class Order {
    private Long id;
    private CoffeeType coffeeType;
    private CoffeeSize size;
    private MilkType milkType;
    private boolean onSite;
    private int orderNumber;
    private OrderStatus orderStatus;
}
