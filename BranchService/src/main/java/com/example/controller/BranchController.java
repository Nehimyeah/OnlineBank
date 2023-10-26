package com.example.controller;

import com.example.domain.Branch;
import com.example.integration.BankIntegration;
import com.example.service.IBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "http://127.0.0.1:5173/",
        allowedHeaders = "*", maxAge = 3600,
        allowCredentials = "true"
)
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBranch(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token

        ) {
        branchService.delete(id, token);
        return ResponseEntity.ok("Branch successfully deleted");
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<?> getAllAccounts(@PathVariable Long id, @RequestHeader("Authorization") String token){

        return ResponseEntity.ok(branchService.getAllAccountsByBranch(id, token));
    }

    @GetMapping("/{id}/loan")
    public ResponseEntity<?> getAllLoanAccounts(@PathVariable Long id, @RequestHeader("Authorization") String token){

        return ResponseEntity.ok(branchService.getLoanAccountsByBranch(id, token));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBranch(@PathVariable Long id, @RequestHeader("Authorization") String token){

        return branchService.getBranchByManagerId(id, token);
    }
}
