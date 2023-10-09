package com.example.controller;

import com.example.domain.Branch;
import com.example.repository.BranchRepository;
import com.example.service.BranchService;
import com.example.service.IBranchService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private IBranchService branchService;

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

    @PostMapping("/addBranch")
    public ResponseEntity<?> addNewBranch(@RequestBody Branch branch){

        String branchName = branch.getBranchName();
        Long branchManagerId = branch.getBranchManagerId();

        Optional<Branch> branchOptional = branchRepository.findByBranchManagerId(branch.getBranchManagerId());

        if(!branchOptional.isPresent() && !branchName.isEmpty()){

            branchService.createBranchInfo(branchName, branchManagerId);

            return ResponseEntity.status(200).body("New branch successfully created");
        }

        else{
            return ResponseEntity.status(400).body("Error, there is already an existing branch.");
        }
    }

    @DeleteMapping("/removeBranch/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable Long id){

        Optional<Branch> branchOptional = branchRepository.findById(id);

        if(branchOptional.isPresent()){

            branchService.deleteBranchInfo(id);
            return ResponseEntity.ok("Branch successfully deleted");

        }
        return ResponseEntity.status(404).body("Branch does not exist");
    }

}
