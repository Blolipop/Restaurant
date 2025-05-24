package com.order.Restarant.Controller.login;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")//原位址
    public String greet(HttpServletRequest request){
        return "welcom to new Web  " +request.getSession().getId();//
    }

}
