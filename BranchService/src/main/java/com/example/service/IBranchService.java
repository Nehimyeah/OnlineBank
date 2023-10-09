package com.example.service;

import com.example.domain.Branch;

public interface IBranchService {

    void createBranchInfo(String branchName, Long branchManagerId);
    void createAddressInfo(Long branchId, String city, String state, String street, int zip);

    String branchInfo(Branch branch);

    void deleteBranchInfo(Long id);


}
