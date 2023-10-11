package com.example;

import com.example.constants.Role;
import com.example.model.Address;
import com.example.model.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Hello world!
 *
 */
@SpringBootApplication

@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {
    private final UserService userService;
    public static void main( String[] args )
    {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = User.builder()
                .email("admin@admin.com")
                .firstName("admin")
                .lastName("admin")
                .role(Role.ADMIN)
                .address(Address.builder()
                                .street1("1000 N 4th St")
                                .city("Fairfield")
                                .state("Iowa")
                                .zip("52557")
                                .build())
                .isActive(true)
                .password("admin")
                .build();
        userService.saveAdmins(admin);
    }
}
