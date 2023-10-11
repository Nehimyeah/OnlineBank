package com.example.controller;

import com.example.domain.Branch;
import com.example.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    private IBranchService branchService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findBranchById(@PathVariable Long id){

        Optional<?> branch = branchService.findById(id);

        if(!branch.isPresent()){

            return ResponseEntity.status(404).body("Could not find the branch with id: " + id);
        }

        return new ResponseEntity<>(branch.get(), HttpStatus.OK);

    }

    @GetMapping("/branches")
    public ResponseEntity<?> getAllBranches(){

        return new ResponseEntity<>(branchService.getAllBranches(), HttpStatus.OK);
    }

    @PostMapping("/addBranch")
    public ResponseEntity<?> addNewBranch(@RequestBody Branch branch, @RequestHeader("Authorization") String token){

        branchService.parseToken(token);

        String branchName = branch.getBranchName();
        Long branchManagerId = branch.getBranchManagerId();
        Optional<?> branchOptional = branchService.findByManagerId(branchManagerId);


        if(!branchOptional.isPresent() && !branchName.isEmpty()){

            branchService.createBranchInfo(branchName, branchManagerId);
            return ResponseEntity.status(200).body("New branch successfully created");
        }

        return ResponseEntity.status(400).body("Error, there is already an existing branch.");
    }

    @DeleteMapping("/removeBranch/{id}")
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
