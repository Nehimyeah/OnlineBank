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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Override
    public void save(User user) {
        failIfUserExists(user);
        String encodedPassword = encodePassword(user);
        user.setPassword(encodedPassword);
        user.disable();
        user.setRole(Role.CUSTOMER);
        repository.save(user);
    }

    private String encodePassword(User user) {
        try {
            return this.passwordEncoder.encode(user.getPassword());
        } catch (Exception e){
            log.error("User already exists with this email address: " + LocalDateTime.now());
            throw new RuntimeException("User already exists with this email address: " + user.getEmail());
        }
    }

    @Override
    public void saveAdmins(User user) {
        failIfUserExists(user);
        String encodedPassword = encodePassword(user);
        user.setPassword(encodedPassword);
        repository.save(user);
    }

    private void failIfUserExists(User user) {
        Optional<User> optUser = repository.findUserByEmail(user.getEmail());
        if (optUser.isPresent())
            throw new RuntimeException("Account already available");
    }


    @Override
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("No user with this email"));
    }

    public Iterable<User> getAll() {
        return repository.findAll();
    }

    public TokenData login(UserCredentials userCredentials) {
        Optional<User> optUser = repository.findUserByEmail(userCredentials.getEmail());
        User user = optUser.orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        return jwtUtil.generateToken(user);
    }

    @Override
    public void createTeam(User user, String token) {
            User loggedInUser = getPrincipal(token);
            if (!Role.ADMIN.equals(loggedInUser.getRole()))
                throw new RuntimeException("No sufficient Access for this operation");
            saveAdmins(user);
    }

    private User getPrincipal(String token) {
        String[] parts = token.split(" ");

        if (parts.length != 2 || !"Bearer".equals(parts[0])) {
            log.error("Invalid header: Incorrect Authentication Structure at:" + LocalDateTime.now());
            throw new RuntimeException("Incorrect Authentication Structure");
        }
        return jwtUtil.parseToken(parts[1]);
    }

    @Override
    public void disable(long userId, String token) {
        User principal = getPrincipal(token);
        System.out.println(principal);
        if (principal.isActive()) {
            if (principal.getRole().equals(Role.MANAGER) || principal.getRole().equals(Role.ADMIN)){
                User user = repository.findUserById(userId).orElseThrow(() -> new RuntimeException("Invalid User"));
                user.disable();
                repository.save(user);
            } else {
                new RuntimeException("No sufficient privilege to perform the required action");
            }
        } else {
            throw new RuntimeException("Inactive user cannot perform operation");
        }
    }

    @Override
    public void enable(long userId, String token) {
        User principal = getPrincipal(token);
        System.out.println(principal);
        if (principal.isActive()) {
            if (principal.getRole().equals(Role.MANAGER) || principal.getRole().equals(Role.ADMIN)){
                User user = repository.findUserById(userId).orElseThrow(() -> new RuntimeException("Invalid User"));
                user.enable();
                repository.save(user);
            } else {
                new RuntimeException("No sufficient privilege to perform the required action");
            }
        } else {
            throw new RuntimeException("Inactive user cannot perform operation");
        }
    }

    @Override
    public Iterable<User> getAllTeam() {
        return repository.getAllByRoleNot(Role.CUSTOMER);
    }
}
