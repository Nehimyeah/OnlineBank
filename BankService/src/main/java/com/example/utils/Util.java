package com.example.utils;

import com.example.dto.User;
import com.example.entity.Account;
import com.example.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;



@Slf4j
public class Util {
    private final static String KEY = "Super_Secret_Key";
    public static String generateAccountNum(){
        Random rnd = new Random();
        int n = 1000000 + rnd.nextInt(9000000);
        return Integer.toString(n);
    }

    public static boolean validate(Account checkingAccount, BigDecimal existingAmount) {
        if (checkingAccount.getBalance().compareTo(BigDecimal.ZERO) < 0 || checkingAccount.getBalance().compareTo(existingAmount) < 0) {
            return false;
        }
        return true;
    }
    public static User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return User.builder()
                    .email(body.getSubject())
                    .id(Long.parseLong((String) body.get("id")))
                    .firstName((String) body.get("firstName"))
                    .lastName((String) body.get("lastName"))
                    .role(Role.valueOf((String)body.get("role")))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Not a valid token");
        }
    }
    public static User getPrincipal(String token) {
        String[] parts = token.split(" ");

        if (parts.length != 2 || !"Bearer".equals(parts[0])) {
            log.error("Invalid header: Incorrect Authentication Structure at:" + LocalDateTime.now());
            throw new RuntimeException("Incorrect Authentication Structure");
        }
        return parseToken(parts[1]);
    }
}
