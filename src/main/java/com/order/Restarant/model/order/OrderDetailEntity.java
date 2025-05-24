package com.order.Restarant.model.order;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetailEntity {


    @EmbeddedId
    private OrderDetailId id =new OrderDetailId();//訂單編號

    @ManyToOne // 每一個orderID  可以擁有多個明細
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne//每一個 餐點可以有多的dish id
    @JoinColumn(name = "dish_id")
    private DishEntity dish;


    private Integer count;

    private Integer total;

    @Embeddable
    @Data
    public static class OrderDetailId implements Serializable {
        @Column(name = "_order_id")//每一個order ID都會有一個複鍵dno
        private  Long orderId;

        @Column(name = "dno")
        private Integer dno;//序號

        @Override
        public String toString() {
            return "OrderDetailEntity{";
        }
    }

}





