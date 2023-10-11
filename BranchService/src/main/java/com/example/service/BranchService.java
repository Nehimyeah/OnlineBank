package com.example.service;

import com.example.domain.Address;
import com.example.domain.Branch;
import com.example.repository.AddressRepository;
import com.example.repository.BranchRepository;
import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonString;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BranchService implements IBranchService {

    @Autowired
    private BranchRepository branchRepository;

    private final static String KEY = "Super_Secret_Key";

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void createBranchInfo(String branchName, Long branchManagerId){

        Branch branch = new Branch(branchName, branchManagerId);

        branchRepository.save(branch);
    }

    @Override
    public void createAddressInfo(Long branchId, String city, String state, String street, int zip){

        Branch branch = branchRepository.findById(branchId).orElse(null);
        Address address = new Address(city, state, street, zip);

        if(branch != null){

            branch.setBranchId(branchId);
            branch.setAddress(address);
            addressRepository.save(address);
            branchRepository.save(branch);
        }

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
    public Optional<Branch> findById(Long id) {

        return branchRepository.findById(id);

    }

    @Override
    public Optional<Branch> findByManagerId(Long branchManagerId) {

        return branchRepository.findByBranchManagerId(branchManagerId);
    }

    @Override
    public List<Branch> getAllBranches() {

        return branchRepository.getAllBranches();
    }

    @Override
    public String parseToken(String token) {

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody();

            String role = (String) body.get("role");

            if("ADMIN".equals(role)){

                return role;
            }
            else {
                throw new RuntimeException("Role is not ADMIN"); // Role doesn't match "ADMIN"
            }


        } catch (Exception e) {
            throw new RuntimeException("Not a valid token");
        }
    }

}
