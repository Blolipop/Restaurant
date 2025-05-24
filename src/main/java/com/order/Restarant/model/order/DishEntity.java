package com.order.Restarant.model.order;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;

//每一個餐點定義 售價 解釋 名稱 ID
@Entity
@Table(name ="dish")
@Data
public class DishEntity {
    @Id
    @GeneratedValue
    private Long Id ;

    private String name;
    private String description;
    private Integer price;

    @ManyToOne
    @JoinColumn(name ="type_id")
    private DishTypeEntity dishType;

}
