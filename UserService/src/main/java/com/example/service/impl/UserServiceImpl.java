package com.example.service.impl;

import com.example.constants.Role;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void save(User user) {
        try {
            String encodedPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            repository.save(user);
        } catch (Exception e){
            log.error("User already exists with this email address: " + LocalDateTime.now());
            throw new RuntimeException("User already exists with this email address: " + user.getEmail());
        }
        user.setActive(false);
        user.setRole(Role.CUSTOMER);
        repository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public Iterable<User> getAll() {
        return repository.findAll();
    }
}
