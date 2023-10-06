package com.example;

import com.example.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EntityScan("com.example.domain")
@EnableJpaRepositories("com.example.repository")
public class BranchServiceApplication implements CommandLineRunner{


    @Autowired
    private BranchService branchService;

    public static void main( String[] args ){
        SpringApplication.run(BranchServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        branchService.createBranchInfo("ABC", 101L);

        branchService.createAddressInfo(1L, "Fairfield", "IA", "123 St", 52556);



    }
}
