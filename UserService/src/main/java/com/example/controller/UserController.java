package com.example.controller;

import com.example.dto.TokenData;
import com.example.dto.UserCredentials;
import com.example.model.User;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping()
    public ResponseEntity<?> createCustomer(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok("Perfectly created");
    }

    @GetMapping
    public Iterable<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials user) {
        TokenData data = userService.login(user);
        System.out.println(jwtUtil.parseToken(data.getToken()));
        return ResponseEntity.ok(data);
    }

    @PostMapping("/teams")
    public ResponseEntity<?> createTeams(@RequestBody User user, @RequestHeader("Authorization") String token) {
        userService.createTeam(user, token);
        return ResponseEntity.ok().build();

    }
}
