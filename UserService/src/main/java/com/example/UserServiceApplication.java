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

import java.util.Random;


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
        generate();
        createAdmin();
        createCustomer();
        createManager();
        createTeller();
    }

    private void createTeller() {
        User customer = User.builder()
                .email("teller@tel.com")
                .firstName("Teller")
                .lastName("Teller")
                .role(Role.TELLER)
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

    private void createCustomer() {
        User teller = User.builder()
                .email("customer@cus.com")
                .firstName("Customer")
                .lastName("Customer")
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

    public void generate() {
        User user1 = User.builder()
                .email("user1@example.com")
                .firstName("John")
                .lastName("Doe")
                .role(Role.MANAGER)
                .address(Address.builder()
                        .street1("123 Main St")
                        .city("Sample City")
                        .state("Sample State")
                        .zip("12345")
                        .build())
                .isActive(true)
                .password("password1")
                .build();
        userService.saveAdmins(user1);

        User user2 = User.builder()
                .email("user2@example.com")
                .firstName("Jane")
                .lastName("Smith")
                .role(Role.CUSTOMER)
                .address(Address.builder()
                        .street1("456 Elm St")
                        .city("Another City")
                        .state("Another State")
                        .zip("67890")
                        .build())
                .isActive(false)
                .password("password2")
                .build();
        userService.saveAdmins(user2);

        User user3 = User.builder()
                .email("user3@example.com")
                .firstName("Alice")
                .lastName("Johnson")
                .role(Role.ADMIN)
                .address(Address.builder()
                        .street1("789 Oak St")
                        .city("Yet Another City")
                        .state("Yet Another State")
                        .zip("54321")
                        .build())
                .isActive(true)
                .password("password3")
                .build();
        userService.saveAdmins(user3);

        User user4 = User.builder()
                .email("user4@example.com")
                .firstName("Bob")
                .lastName("Brown")
                .role(Role.MANAGER)
                .address(Address.builder()
                        .street1("101 Pine St")
                        .city("Cityville")
                        .state("Statetown")
                        .zip("98765")
                        .build())
                .isActive(false)
                .password("password4")
                .build();
        userService.saveAdmins(user4);

        User user5 = User.builder()
                .email("user5@example.com")
                .firstName("Eva")
                .lastName("Martinez")
                .role(Role.CUSTOMER)
                .address(Address.builder()
                        .street1("246 Cedar St")
                        .city("Townsville")
                        .state("Countyland")
                        .zip("13579")
                        .build())
                .isActive(true)
                .password("password5")
                .build();
        userService.saveAdmins(user5);

        User user6 = User.builder()
                .email("user6@example.com")
                .firstName("Michael")
                .lastName("Lee")
                .role(Role.ADMIN)
                .address(Address.builder()
                        .street1("777 Birch St")
                        .city("Villageville")
                        .state("Territorytown")
                        .zip("86420")
                        .build())
                .isActive(false)
                .password("password6")
                .build();
        userService.saveAdmins(user6);

        User user7 = User.builder()
                .email("user7@example.com")
                .firstName("Olivia")
                .lastName("Davis")
                .role(Role.MANAGER)
                .address(Address.builder()
                        .street1("888 Willow St")
                        .city("Hamletsville")
                        .state("Provincetown")
                        .zip("24680")
                        .build())
                .isActive(true)
                .password("password7")
                .build();
        userService.saveAdmins(user7);

        User user8 = User.builder()
                .email("user8@example.com")
                .firstName("William")
                .lastName("Wilson")
                .role(Role.CUSTOMER)
                .address(Address.builder()
                        .street1("555 Redwood St")
                        .city("Villagetown")
                        .state("Territoryland")
                        .zip("97531")
                        .build())
                .isActive(false)
                .password("password8")
                .build();
        userService.saveAdmins(user8);

        User user9 = User.builder()
                .email("user9@example.com")
                .firstName("Sophia")
                .lastName("Anderson")
                .role(Role.ADMIN)
                .address(Address.builder()
                        .street1("321 Cedar St")
                        .city("Riverdale")
                        .state("Countyland")
                        .zip("54321")
                        .build())
                .isActive(true)
                .password("password9")
                .build();
        userService.saveAdmins(user9);

        User user10 = User.builder()
                .email("user10@example.com")
                .firstName("James")
                .lastName("Taylor")
                .role(Role.MANAGER)
                .address(Address.builder()
                        .street1("777 Elm St")
                        .city("Greenville")
                        .state("Territorytown")
                        .zip("12345")
                        .build())
                .isActive(false)
                .password("password10")
                .build();
        userService.saveAdmins(user10);

        User user11 = User.builder()
                .email("user11@example.com")
                .firstName("Emily")
                .lastName("Brown")
                .role(Role.CUSTOMER)
                .address(Address.builder()
                        .street1("123 Oak St")
                        .city("Sample City")
                        .state("Sample State")
                        .zip("67890")
                        .build())
                .isActive(true)
                .password("password11")
                .build();
        userService.saveAdmins(user11);

        User user12 = User.builder()
                .email("user12@example.com")
                .firstName("Daniel")
                .lastName("Johnson")
                .role(Role.ADMIN)
                .address(Address.builder()
                        .street1("456 Maple St")
                        .city("Another City")
                        .state("Another State")
                        .zip("98765")
                        .build())
                .isActive(false)
                .password("password12")
                .build();
        userService.saveAdmins(user12);

        User user13 = User.builder()
                .email("user13@example.com")
                .firstName("Olivia")
                .lastName("Davis")
                .role(Role.MANAGER)
                .address(Address.builder()
                        .street1("789 Pine St")
                        .city("Yet Another City")
                        .state("Yet Another State")
                        .zip("54321")
                        .build())
                .isActive(true)
                .password("password13")
                .build();
        userService.saveAdmins(user13);

        User user14 = User.builder()
                .email("user14@example.com")
                .firstName("Liam")
                .lastName("Hernandez")
                .role(Role.CUSTOMER)
                .address(Address.builder()
                        .street1("101 Oak St")
                        .city("Cityville")
                        .state("Statetown")
                        .zip("13579")
                        .build())
                .isActive(false)
                .password("password14")
                .build();
        userService.saveAdmins(user14);

        User user15 = User.builder()
                .email("user15@example.com")
                .firstName("Mia")
                .lastName("Garcia")
                .role(Role.ADMIN)
                .address(Address.builder()
                        .street1("246 Pine St")
                        .city("Townsville")
                        .state("Countyland")
                        .zip("86420")
                        .build())
                .isActive(true)
                .password("password15")
                .build();
        userService.saveAdmins(user15);

// Continue with additional

    }
}
