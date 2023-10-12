package com.example.service;

import com.example.domain.Branch;

import java.util.List;
import java.util.Optional;

public interface IBranchService {

//    void createBranchInfo(String branchName, Long branchManagerId);
//    void createAddressInfo(Long branchId, String city, String state, String street, int zip);
    void create(Branch branch, String bearerToken);
    String branchInfo(Branch branch);

    void deleteBranchInfo(Long id);

    Branch findById(Long id, String token);

    Optional<Branch> findByManagerId(Long branchManagerId);

    List<Branch> getAllBranches(String token);


}
