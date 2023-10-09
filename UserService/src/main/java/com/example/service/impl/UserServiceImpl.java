package com.example.service.impl;

import com.example.constants.Role;
import com.example.dto.TokenData;
import com.example.dto.UserCredentials;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


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

    public TokenData login(UserCredentials userCredentials) {
        User user = repository.findUserByEmail(userCredentials.getEmail());
//        System.out.println(user);
        if (user == null) {
            System.out.println("here");
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        return jwtUtil.generateToken(user);
    }

    @Override
    public void createTeam(User user, String token) {
            String[] parts = token.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                log.error("Invalid header: Incorrect Authentication Structure at:" + LocalDateTime.now());
                throw new RuntimeException("Incorrect Authentication Structure");
            }
            User loggedInUser = jwtUtil.parseToken(parts[1]);
            if (!Role.ADMIN.equals(loggedInUser.getRole()))
                throw new RuntimeException("No sufficient Access for this operation");
            repository.save(user);
    }
}
