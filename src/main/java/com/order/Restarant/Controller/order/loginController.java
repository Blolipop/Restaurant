package com.order.Restarant.Controller.order;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class loginController {

    @GetMapping("/register")
    public String RegisterView(){
        return "register";
    }

    @GetMapping("/login")
    public String LoginView(){
        return "login";
    }

//    @GetMapping("/api/redirect")
//    public String redirect() {
//        return "redirect:/orders"; // 自動導頁
//    }


}
