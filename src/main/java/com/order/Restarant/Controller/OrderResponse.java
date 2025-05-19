package com.order.Restarant.Controller;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private int totalPrice;
    private LocalDateTime dateTime;
}