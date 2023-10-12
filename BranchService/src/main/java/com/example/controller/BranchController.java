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
    public ResponseEntity<?> findById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(branchService.findById(id, token));
    }

    @GetMapping
    public ResponseEntity<?> getAllBranches(@RequestHeader("Authorization") String bearerToken){
        return ResponseEntity.ok(branchService.getAllBranches(bearerToken));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Branch branch, @RequestHeader("Authorization") String token){
        branchService.create(branch, token);
        return ResponseEntity.ok("Branch created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable long id,
            @RequestBody Branch branch,
            @RequestHeader("Authorization") String token
    ) {
        branchService.update(id, branch, token);
        return ResponseEntity.ok("Branch data updated successfully");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBranch(@PathVariable Long id) {

//        Optional<?> branchOptional = branchService.findById(id);

//        if (branchOptional.isPresent()) {

//            branchService.deleteBranchInfo(id);
//            return ResponseEntity.ok("Branch successfully deleted");

//        }
//        return ResponseEntity.status(404).body("Branch does not exist.");
    return ResponseEntity.ok().build();
    }

//    @PutMapping("/update-manager-id")
//    public ResponseEntity<?> updateManageID(){
//
//        return null;
//    }

}
