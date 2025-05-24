package com.order.Restarant.Controller.login;


import com.order.Restarant.Service.login.Userservice;
import com.order.Restarant.model.login.Users;
import com.order.Restarant.model.order.DishEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private Userservice service;

    @PostMapping("/register")
    public ResponseEntity<Users> newUser(@RequestBody Users user) {
        return ResponseEntity.ok(service.register(user));
    }
//    @GetMapping("/test-auth")
//    public String testAuth(Authentication auth) {
//        System.out.println("目前使用者: " + auth.getName());
//        auth.getAuthorities().forEach(a -> System.out.println("權限: " + a.getAuthority()));
//        return "ok";
//    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        try {

            System.out.println("Login API called with user: " + user.getUsername());


            String token = String.valueOf(service.verify(user));
            return ResponseEntity.ok(Map.of("token", token)); // 回傳 JSON token
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號或密碼錯誤");
        }
    }


        @GetMapping("/dish")
        public ResponseEntity<Void> verifyToken(Authentication auth) {
            // 不回資料，只驗證 token 合法性
            return ResponseEntity.ok().build();
        }




//
//    @PostMapping("/register")//javascipt
//    public Users newUser(@RequestBody Users user){
//
//        return service.register(user);
//    }
//
//    @PostMapping("/login")
//    public String Userlogin(@RequestBody Users user){
//        System.out.println("Login API called with user: " + user.getUsername());
//
//        return service.verify(user);
//    }

}
