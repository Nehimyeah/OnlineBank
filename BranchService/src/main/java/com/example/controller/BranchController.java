package com.example.controller;

import com.example.domain.Branch;
import com.example.repository.BranchRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchRepository branchRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findBranchById(@PathVariable Long id){

        Optional<Branch> branch = branchRepository.findById(id);

        if(!branch.isPresent()){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(branch.get(), HttpStatus.OK);

    }

    @GetMapping("/branches")
    public ResponseEntity<?> getAllBranches(){

        return new ResponseEntity<>(branchRepository.getAllBranches(), HttpStatus.OK);
    }
}
