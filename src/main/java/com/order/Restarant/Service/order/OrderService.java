package com.order.Restarant.Service.order;


import com.order.Restarant.Controller.order.OrderDetailsRequest;
import com.order.Restarant.Controller.order.OrderResponse;


import com.order.Restarant.Repo.order.DishRepository;
import com.order.Restarant.Repo.order.OrderRepository;
import com.order.Restarant.model.order.OrderDetailEntity;
import com.order.Restarant.model.order.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

    // 新增訂單並返回訂單號碼
    public OrderResponse placeOrder(List<OrderDetailsRequest> orderItems) {
        // 創建訂單實體
        System.out.println("orderplacing");
        OrderEntity order = new OrderEntity();
        order.setDateTime(LocalDateTime.now());

        OrderEntity orderDB = orderRepository.save(order);//訂單放入歷史訂單列

        int totalPrice = 0;
        int index = 0;
        for (OrderDetailsRequest item : orderItems) {//網頁輸出訂單細項陣列
            var dish = dishRepository.findById(item.getDishId()).orElse(null);
            System.out.println(item.getDishId());
            var subTotal = item.getPrice() * item.getCount();//裡面的count 跟price
            if (dish == null) continue;

            OrderDetailEntity orderDetail = new OrderDetailEntity();//新增一個訂單
            orderDetail.getId().setOrderId(orderDB.getId());
            orderDetail.getId().setDno(index);
            index += 1;
            orderDetail.setOrder(orderDB);
            orderDetail.setDish(dish);
            orderDetail.setCount(item.getCount());
            orderDetail.setTotal(subTotal);

            // 設定訂單項目關聯至訂單
            orderDB.addOrderDetail(orderDetail);

            totalPrice += subTotal;
        }

        orderDB.setTotalPrice(totalPrice);

        // 儲存訂單至資料庫
        orderRepository.save(orderDB);

        return OrderResponse.builder()
                .orderId(orderDB.getId())
                .build();
    }

    // 獲取所有訂單
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    // 根據訂單ID獲取訂單
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    // 刪除訂單
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}