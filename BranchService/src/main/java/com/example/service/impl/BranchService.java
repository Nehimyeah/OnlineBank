package com.example.service.impl;

import com.example.domain.Address;
import com.example.domain.Branch;
import com.example.dto.User;
import com.example.repository.BranchRepository;
import com.example.service.IBranchService;
import com.example.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService {

    private final BranchRepository branchRepository;
    private final JwtUtil jwtUtil;

//    @Override
//    public void createBranchInfo(String branchName, Long branchManagerId){
//
//        Branch branch = new Branch(branchName, branchManagerId);
//
//        branchRepository.save(branch);
//    }

//    @Override
//    public void createAddressInfo(Long branchId, String city, String state, String street, int zip){
//
//        Branch branch = branchRepository.findById(branchId).orElse(null);
//        Address address = new Address(city, state, street, zip);
//
//        if(branch != null){
//
//            branch.setBranchId(branchId);
//            branch.setAddress(address);
//            addressRepository.save(address);
//            branchRepository.save(branch);
//        }
//
//    }

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
    public String branchInfo(Branch branch){

        return branch.getBranchName() + ", " + branch.getBranchManagerId();
    }

    @Override
    public void deleteBranchInfo(Long id) {

        Optional<Branch> branchOptional = branchRepository.findById(id);

        branchRepository.delete(branchOptional.get());
    }

    @Override
    public Branch findById(Long id, String bearerToken) {
        authenticate(bearerToken);
        Optional<Branch> branch = branchRepository.findById(id);
        return branch.orElseThrow(() -> new RuntimeException("Branch with this information doesn't exist"));
    }

    @Override
    public Optional<Branch> findByManagerId(Long branchManagerId) {

        return branchRepository.findByBranchManagerId(branchManagerId);
    }

    @Override
    public List<Branch> getAllBranches(String bearerToken) {
        authenticate(bearerToken);
        return branchRepository.findAll();
    }

    private void authenticate(String bearerToken) {
        String token = jwtUtil.extractToken(bearerToken);
        User principal = jwtUtil.parseToken(token);
        if (!principal.getRole().equals("ADMIN"))
            throw new RuntimeException("User can't perform this operation");
    }
}
