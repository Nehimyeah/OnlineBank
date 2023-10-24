package com.example.service;

import com.example.domain.Branch;
import com.example.dto.ResponseAccountInfo;

import java.util.List;

public interface IBranchService {

    void create(Branch branch, String bearerToken);

    Branch findById(Long id, String token);

    List<Branch> getAllBranches(String token);

    void update(long id, Branch branch, String token);

    void delete(Long id, String token);

    void saveInternal(Branch branch);

    ResponseAccountInfo getAllAccountsByBranch(Long id, String token);
}
