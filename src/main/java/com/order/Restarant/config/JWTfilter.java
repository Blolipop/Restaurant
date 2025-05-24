package com.order.Restarant.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.order.Restarant.Service.login.JWTservice;
import com.order.Restarant.Service.login.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTfilter extends OncePerRequestFilter {

    @Autowired
    private JWTservice jwtService;
    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();//請求跟登入的網址
        System.out.println("Request URI: " + path);

        // 如果是這些不需要驗證的路徑，就直接放行
        if (path.endsWith("/api/login") || path.endsWith("/api/register") ||
                path.equals("/login") || path.equals("/register") ||
                path.equals("/favicon.ico") || path.startsWith("/css") ||
                path.startsWith("/js") || path.startsWith("/images") ||
                path.startsWith("/static")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = null ;
        String username = null;
        String Authheader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + Authheader);



        if (Authheader != null && Authheader.startsWith("Bearer")){
            token = Authheader.substring(7);
            username = jwtService.extractUsername(token);//  注意
            System.out.println("Extracted username from token: " + username);

        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //也就是登入狀態）。簡單來說，它就是「保存與取得目前使用者身份資訊的地方」。

            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            //getbean 從純java檔案中引用出來
            if (jwtService.vaildToken(token, userDetails)) {

                // ✅ 從 JWT 中解出 roles，轉成權限物件
                List<String> roles = jwtService.extractRoles(token);// 你需要寫這個方法

                System.out.println("Extracted roles from token: " + roles);
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }            filterChain.doFilter(request, response);

    }
}
