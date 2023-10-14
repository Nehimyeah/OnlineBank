package com.example.utils;

import com.example.constants.Role;
import com.example.dto.TokenData;
import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {


    private final static String KEY = "Super_Secret_Key";

    @Value("${validity}")
    private final static int VALIDITY = 60;

    public TokenData generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("role", String.valueOf(user.getRole()));
        claims.put("id", String.valueOf(user.getId()));
        claims.put("isActive", String.valueOf(user.isActive()));
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + VALIDITY * 1000 * 60 * 10;
        Date exp = new Date(expMillis);

        String a = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
        return new TokenData(a);
    }

    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return User.builder()
                    .email(body.getSubject())
                    .firstName((String) body.get("firstName"))
                    .lastName((String) body.get("lastName"))
                    .role(Role.valueOf((String)body.get("role")))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Not a valid token");
        }
    }

    public void validateToken(final String header){

        try {
            String[] parts = header.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                log.error("Invalid header: Incorrect Authentication Structure at:" + LocalDateTime.now());
//                throw new JwtTokenIncorrectStructureException("Incorrect Authentication Structure");
                return;
            }

            Jws<Claims> result = Jwts.parser().setSigningKey(KEY)
                    .parseClaimsJws(parts[1]);

            User user = new ObjectMapper().readValue(result.getBody().getSubject(), User.class);
            System.out.println(user);
//            return user;

        } catch (Exception ex) {
            log.error("Json parser failed: Invalid JWT signature at:" + LocalDateTime.now());
        }
    }

}
