package com.example.repository;

import com.example.constants.Role;
import com.example.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(long userId);

    List<User> getAllByRole(Role role);

    List<User> getAllByRoleNot(Role role);
}
