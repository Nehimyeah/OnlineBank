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
        Address address = Address.builder()
                .zip(52557)
                .city("Fairfield")
                .state("Iowa")
                .street1("1000 N 4th St")
                .build();
        Branch branch = Branch.builder()
                .branchManagerId(10L)
                .branchManagerName("Henry Steinfeld")
                .branchName("Fairfield")
                .address(address)
                .build();
        branchService.saveInternal(branch);
    }
}
