package com.example;

import com.example.constants.Role;
import com.example.model.Address;
import com.example.model.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {
    private final UserService userService;
    public static void main( String[] args )
    {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createAdmin();
        createCustomer();
        createManager();
        createTeller();
    }

    private void createCustomer() {
        User customer = User.builder()
                .email("customer@cus.com")
                .firstName("customer")
                .lastName("customer")
                .role(Role.CUSTOMER)
                .address(Address.builder()
                        .street1("1000 N 4th St")
                        .city("Fairfield")
                        .state("Iowa")
                        .zip("52557")
                        .build())
                .isActive(false)
                .password("1")
                .build();
        userService.saveAdmins(customer);
    }

    private void createTeller() {
        User teller = User.builder()
                .email("customer@cus.com")
                .firstName("customer")
                .lastName("customer")
                .role(Role.CUSTOMER)
                .address(Address.builder()
                        .street1("1000 N 4th St")
                        .city("Fairfield")
                        .state("Iowa")
                        .zip("52557")
                        .build())
                .isActive(true)
                .password("1")
                .build();
        userService.saveAdmins(teller);
    }

    private void createAdmin() {
        User admin = User.builder()
                .email("admin@admin.com")
                .firstName("Admin")
                .lastName("Admin")
                .role(Role.ADMIN)
                .address(Address.builder()
                        .street1("1000 N 4th St")
                        .city("Fairfield")
                        .state("Iowa")
                        .zip("52557")
                        .build())
                .isActive(true)
                .password("1")
                .build();
        userService.saveAdmins(admin);
    }

    private void createManager() {
        User manager = User.builder()
                .email("manager@man.com")
                .firstName("Manager")
                .lastName("Manager")
                .role(Role.MANAGER)
                .address(Address.builder()
                        .street1("1000 N 4th St")
                        .city("Fairfield")
                        .state("Iowa")
                        .zip("52557")
                        .build())
                .isActive(true)
                .password("1")
                .build();
        userService.saveAdmins(manager);
    }
}
