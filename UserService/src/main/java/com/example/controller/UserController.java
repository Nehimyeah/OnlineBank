package com.example.controller;

import com.example.dto.UserCredentials;
import com.example.model.User;
import com.example.service.UserService;
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
        System.out.println(user);
        return ResponseEntity.ok(userService.login(user));
    }
}
