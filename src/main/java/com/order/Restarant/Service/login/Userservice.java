package com.order.Restarant.Service.login;

import com.order.Restarant.Repo.login.RoleRepository;
import com.order.Restarant.Repo.login.UserRepo;
import com.order.Restarant.model.login.Role;
import com.order.Restarant.model.login.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class Userservice {
    @Autowired
    private UserRepo repo;

    @Autowired
    JWTservice jwtservice;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(15);

    public Users register(Users user){
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("找不到角色 ROLE_USER"));
        user.setRoles(Set.of(defaultRole));
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);

    }
    public String verify(Users loginUser) {
        // 驗證帳號密碼
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(), loginUser.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            // 🔥 用 username 再查一次真正的 Users（包含 roles）
            Users userWithRoles = repo.findByUsername(loginUser.getUsername());
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 傳給 JWTservice 製作 Token
            return jwtservice.generateToken(userWithRoles);
        }

        return null;
    }

//    public String verify(Users user) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        if (authentication.isAuthenticated()) {
//            // 重點：這裡用 authentication 裡面的 userDetails
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//            // 再從 userDetails 轉回完整的 Users 資料（含 roles）
//            Users dbUser = repo.findByUsername(userDetails.getUsername());
//
//            System.out.println("Roles in token: " + dbUser.getRoles()); // ✅ 現在應該有值了
//
//            return jwtservice.generateToken(dbUser); // 用正確含角色的 user 去產生 token
//        }
//
//        return "fail";
//    }


//    public String verify(Users user) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//            System.out.println("try to login");
//
//            if (authentication.isAuthenticated()) {
//                return jwtservice.generateToken(user);
//            }
//
//            return "not authenticated";
//
//        } catch (BadCredentialsException ex) {
//            System.out.println("密碼錯誤：" + ex.getMessage());
//            return "password error";
//        } catch (UsernameNotFoundException ex) {
//            System.out.println("使用者不存在：" + ex.getMessage());
//            return "user not found";
//        } catch (Exception ex) {
//            System.out.println("其他登入錯誤：" + ex.getMessage());
//            ex.printStackTrace();
//            return "login error: " + ex.getMessage();
//        }
//    }
}
