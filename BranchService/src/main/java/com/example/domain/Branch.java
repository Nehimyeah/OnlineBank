package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;
    private String branchName;
    private Long branchManagerId;

    @OneToOne(fetch = FetchType.EAGER)
    private Address address;

    public Branch(String branchName, Long branchManagerId) {
        this.branchName = branchName;
        this.branchManagerId = branchManagerId;
    }

    public Branch() {
    }


    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getBranchManagerId() {
        return branchManagerId;
    }

    public void setBranchManagerId(Long branchManagerId) {
        this.branchManagerId = branchManagerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                ", branchManagerId=" + branchManagerId +
                ", address=" + address +
                '}';
    }
}
