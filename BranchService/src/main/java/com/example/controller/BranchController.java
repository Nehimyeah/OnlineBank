package com.example.controller;

import com.example.domain.Branch;
import com.example.service.IBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {

    private final IBranchService branchService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findBranchById(@PathVariable Long id){

        Optional<?> branch = branchService.findById(id);

        if(!branch.isPresent()){

            return ResponseEntity.status(404).body("Could not find the branch with id: " + id);
        }

        return new ResponseEntity<>(branch.get(), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<?> getAllBranches(@RequestHeader("Authorization") String bearerToken){
        return ResponseEntity.ok(branchService.getAllBranches(bearerToken));
    }

    @PostMapping
    public ResponseEntity<?> addNewBranch(@RequestBody Branch branch, @RequestHeader("Authorization") String token){


        String branchName = branch.getBranchName();
        Long branchManagerId = branch.getBranchManagerId();
        Optional<?> branchOptional = branchService.findByManagerId(branchManagerId);


        if(!branchOptional.isPresent() && !branchName.isEmpty()){

            branchService.createBranchInfo(branchName, branchManagerId);
            return ResponseEntity.status(200).body("New branch successfully created");
        }

        return ResponseEntity.status(400).body("Error, there is already an existing branch.");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBranch(@PathVariable Long id) {

        Optional<?> branchOptional = branchService.findById(id);

        if (branchOptional.isPresent()) {

            branchService.deleteBranchInfo(id);
            return ResponseEntity.ok("Branch successfully deleted");

        }
        return ResponseEntity.status(404).body("Branch does not exist.");
    }

//    @PutMapping("/update-manager-id")
//    public ResponseEntity<?> updateManageID(){
//
//        return null;
//    }

}
