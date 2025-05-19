package com.order.Restarant.Controller;

import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class helloWorld {


    @GetMapping("/hello")
    public String helloController (){

        return "hello world";

    }

    @PostMapping("/nonhello")
    public String jjj(@RequestBody  OrderDetailsRequest orderDetailsRequest){
        System.out.println(orderDetailsRequest.getDishId());
        return  "hello";
    }
}
