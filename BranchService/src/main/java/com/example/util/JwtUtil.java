package com.example.util;

import com.example.dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class JwtUtil {
    private final static String KEY = "Super_Secret_Key";


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
                    .role((String)body.get("role"))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Not a valid token");
        }
    }

    public String extractToken(final String tokenHeader){

        try {
            String[] parts = tokenHeader.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                log.error("Invalid header: Token has incorrect Structure at: " + tokenHeader);
                throw new Exception("Token not provided");
            }
            return parts[1];

        } catch (Exception e) {
            log.error("Json parser failed: Invalid JWT signature at:" + LocalDateTime.now());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
