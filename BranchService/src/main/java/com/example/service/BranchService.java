package com.example.service;

import com.example.error.BranchNotFoundException;
import com.example.domain.Address;
import com.example.domain.Branch;
import com.example.repository.AddressRepository;
import com.example.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BranchService implements IBranchService {

    @Autowired
    private BranchRepository branchRepository;
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
}
