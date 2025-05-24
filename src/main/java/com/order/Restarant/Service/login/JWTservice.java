package com.order.Restarant.Service.login;


import com.order.Restarant.model.login.Role;
import com.order.Restarant.model.login.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTservice {

    private String secretkey = "";

    public JWTservice() {//JWT keygenerator  生產key by hmacsha256

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();

        // 把角色名稱加入 claims（ex: ROLE_USER, ROLE_ADMIN）
        claims.put("roles", user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList())
        );
        System.out.println("正在產生Token");
        System.out.println("Roles in token: " + user.getRoles());


        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 小時
                .and()
                .signWith(getkey())
                .compact();
    }

    private SecretKey getkey() {//宣告key轉byte
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
         return extractClaim(token, Claims::getSubject);
    }

    public boolean vaildToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {//token "個別"解析
        final Claims claims = extractAllClaims(token);//系統化解析 from 樓下
        return claimResolver.apply(claims); //申請特別解析對象
    }

    private Claims extractAllClaims(String token) {//idk
        return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }//idk 看起來好像是其他驗證方式輸入是給出新的日期並與token比較

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("roles");

        if (rolesObj instanceof List<?>) {
            List<?> rawList = (List<?>) rolesObj;
            return rawList.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .toList();
        }

        return new ArrayList<>();
    }

//這個也是
}

