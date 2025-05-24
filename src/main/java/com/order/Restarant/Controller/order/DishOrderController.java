package com.order.Restarant.Controller.order;


import com.order.Restarant.Service.order.DishService;
import com.order.Restarant.Service.order.OrderService;
import com.order.Restarant.model.order.DishEntity;
import com.order.Restarant.model.order.OrderDetailEntity;
import com.order.Restarant.model.order.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class DishOrderController {
    private final DishService dishService;
    private final OrderService orderService;

    // 顯示新增訂單頁面  可以加入的訂單
    @GetMapping("/create")
    public String createOrder(Model model) {
        List<DishEntity> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        return "create-order";
    }

    // 處理新增訂單的API請求 顯示
    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody List<OrderDetailsRequest> orderItems) {
        return ResponseEntity.ok(orderService.placeOrder(orderItems));
    }

    // 刪除訂單
    @GetMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/orders";
    }

    // 顯示所有訂單
    @GetMapping()
    public String getAllOrders(Model model) {
        List<OrderEntity> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    // 顯示訂單詳細內容
    @GetMapping("/{orderId}")
    public String getOrderDetails(@PathVariable("orderId") Long orderId, Model model) {
        OrderEntity order = orderService.getOrderById(orderId);
        List<OrderDetailEntity> orderDetails = order.getOrderDetails();
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "orders";
    }
}