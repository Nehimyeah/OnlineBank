package com.example.service;

import com.example.dto.TokenData;
import com.example.dto.UserCredentials;
import com.example.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    void save(User user);

    void saveAdmins(User user);

    User findUserByEmail(String email);

    Iterable<User> getAll();

    TokenData login(UserCredentials user);

    void createTeam(User user, String token);

    void disable(long userId, String token);

    void enable(long userId, String token);


}
