package com.example.service.impl;

import com.example.domain.Branch;
import com.example.dto.User;
import com.example.repository.BranchRepository;
import com.example.service.IBranchService;
import com.example.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService {

    private final BranchRepository branchRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void create(Branch branch, String bearerToken) {
        authenticate(bearerToken);
        Optional<Branch> branchOptional = branchRepository.findByBranchName(branch.getBranchName());
        if (branchOptional.isEmpty()) {
            branchRepository.save(branch);
            return;
        }
        throw new RuntimeException("Branch already exists");
    }


    @Override
    public Branch findById(Long id, String bearerToken) {
        authenticate(bearerToken);
        Optional<Branch> branch = branchRepository.findById(id);
        return branch.orElseThrow(() -> new RuntimeException("Branch with this information doesn't exist"));
    }

    @Override
    public List<Branch> getAllBranches(String bearerToken) {
        authenticate(bearerToken);
        return branchRepository.findAll();
    }

    @Override
    public void update(long id, Branch branch, String token) {
        authenticate(token);
        Optional<Branch> branchFromDB = branchRepository.findById(id);
        if (branchFromDB.isPresent()) {
            branch.setBranchId(id);
            branchRepository.save(branch);
            return;
        }
        throw new RuntimeException("Branch data doesn't exist");
    }

    @Override
    public void delete(Long id, String bearerToken) {
        authenticate(bearerToken);
        Optional<Branch> branch = branchRepository.findById(id);
        branchRepository.delete(branch.orElseThrow(() -> new RuntimeException("Branch doesn't exist")));
    }

    @Override
    public void saveInternal(Branch branch) {
        Optional<Branch> branchOptional = branchRepository.findByBranchName(branch.getBranchName());
        if (branchOptional.isEmpty()) {
            branchRepository.save(branch);
            return;
        }
        throw new RuntimeException("Branch already exists");
    }

    private void authenticate(String bearerToken) {
        String token = jwtUtil.extractToken(bearerToken);
        User principal = jwtUtil.parseToken(token);
        if (!principal.getRole().equals("ADMIN"))
            throw new RuntimeException("User can't perform this operation");
    }
}
