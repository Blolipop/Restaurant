package com.order.Restarant.Controller.order;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderDetailsRequest {


    private Long dishId;
    private Integer count;
    private Integer price ;

}
