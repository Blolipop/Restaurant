package com.order.Restarant.model.order;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name ="_order")
@Data
public class OrderEntity {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "Date_time")
    private LocalDateTime dateTime;

    @Column(name = "Total_price")
    private int totalPrice;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
    private  List<OrderDetailEntity> orderDetails = new ArrayList<>();//新增訂單
    //訂單功能增加
    public void addOrderDetail(OrderDetailEntity orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }
    //訂單功能刪除
    public void removeOrderDetails(OrderDetailEntity orderDetail){
        orderDetails.remove(orderDetail);
        orderDetail.setOrder(null);
    }
}
