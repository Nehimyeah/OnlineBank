package com.example.service;

import com.example.domain.Branch;

import java.util.List;
import java.util.Optional;

public interface IBranchService {

    void create(Branch branch, String bearerToken);

    Branch findById(Long id, String token);

    List<Branch> getAllBranches(String token);

    void update(long id, Branch branch, String token);

    void delete(Long id, String token);
}
