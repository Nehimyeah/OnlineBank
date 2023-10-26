package com.example;

import com.example.domain.Address;
import com.example.domain.Branch;
import com.example.service.IBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EntityScan("com.example.domain")
@EnableJpaRepositories("com.example.repository")
@RequiredArgsConstructor
@EnableDiscoveryClient
public class BranchServiceApplication implements CommandLineRunner{


    private final IBranchService branchService;

    public static void main( String[] args ){
        SpringApplication.run(BranchServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        generate();
//        Address address = Address.builder()
//                .zip(52557)
//                .city("Fairfield")
//                .state("Iowa")
//                .street1("1000 N 4th St")
//                .build();
//        Branch branch = Branch.builder()
//                .branchManagerId(1L)
//                .branchManagerName("Henry Steinfeld")
//                .branchName("Fairfield")
//                .address(address)
//                .build();
//        branchService.saveInternal(branch);
    }

    public void generate() {
        // Branch 1
        Address address1 = Address.builder()
                .zip(52557)
                .city("Fairfield")
                .state("Iowa")
                .street1("1000 N 4th St")
                .build();
        Branch branch1 = Branch.builder()
                .branchManagerId(4L)
                .branchManagerName("Henry Steinfeld")
                .branchName("Fairfield")
                .address(address1)
                .build();
        branchService.saveInternal(branch1);

// Branch 2
        Address address2 = Address.builder()
                .zip(54321)
                .city("Cityville")
                .state("Statetown")
                .street1("123 Elm St")
                .build();
        Branch branch2 = Branch.builder()
                .branchManagerId(7L)
                .branchManagerName("Alice Johnson")
                .branchName("Cityville")
                .address(address2)
                .build();
        branchService.saveInternal(branch2);

// Branch 3
        Address address3 = Address.builder()
                .zip(67890)
                .city("Another City")
                .state("Another State")
                .street1("456 Oak St")
                .build();
        Branch branch3 = Branch.builder()
                .branchManagerId(10L)
                .branchManagerName("Bob Brown")
                .branchName("Another City")
                .address(address3)
                .build();
        branchService.saveInternal(branch3);

// Branch 4
        Address address4 = Address.builder()
                .zip(98765)
                .city("Townsville")
                .state("Countyland")
                .street1("789 Pine St")
                .build();
        Branch branch4 = Branch.builder()
                .branchManagerId(13L)
                .branchManagerName("Eva Martinez")
                .branchName("Townsville")
                .address(address4)
                .build();
        branchService.saveInternal(branch4);

// Branch 5
        Address address5 = Address.builder()
                .zip(24680)
                .city("Hamletsville")
                .state("Provincetown")
                .street1("888 Willow St")
                .build();
        Branch branch5 = Branch.builder()
                .branchManagerId(18L)
                .branchManagerName("Michael Lee")
                .branchName("Hamletsville")
                .address(address5)
                .build();
        branchService.saveInternal(branch5);

// Branch 6
        Address address6 = Address.builder()
                .zip(97531)
                .city("Villagetown")
                .state("Territoryland")
                .street1("555 Redwood St")
                .build();
        Branch branch6 = Branch.builder()
                .branchManagerId(1L)
                .branchManagerName("Olivia Davis")
                .branchName("Villagetown")
                .address(address6)
                .build();
        branchService.saveInternal(branch6);

    }
}
