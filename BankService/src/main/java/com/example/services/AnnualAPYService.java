package com.example.services;

import com.example.dto.User;
import com.example.dto.request.AnnualInterestRequest;
import com.example.entity.AnnualAPY;
import com.example.enums.Role;
import com.example.repository.AnnualAPYRepository;
import com.example.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnualAPYService {
    @Autowired
    AnnualAPYRepository apyRepository;

    public ResponseEntity<?> create(AnnualInterestRequest annualInterestRequest,String token){
        try{
            User loggedInUser = Util.getPrincipal(token);
            if (!Role.ADMIN.equals(loggedInUser.getRole()))
                throw new RuntimeException("No sufficient Access for this operation");
            AnnualAPY annualAPY = new AnnualAPY();
            annualAPY.setRateType(annualInterestRequest.getRateType());
            annualAPY.setYear(annualInterestRequest.getYear());
            annualAPY.setAnnualAPY(annualInterestRequest.getAnnualInterest());
            annualAPY.setMonths(annualInterestRequest.getMonths());
            annualAPY.setDescription(annualInterestRequest.getDescription());
            apyRepository.save(annualAPY);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong. Not created");
        }
        return ResponseEntity.status(HttpStatus.OK).body("APY rate saved");
    }

    public double findById(long id) {
        return apyRepository.findById(id).get().getAnnualAPY();
    }

    public ResponseEntity<?> getAPYList() {
//          User loggedInUser = Util.getPrincipal(token);
//        if (!Role.ADMIN.equals(loggedInUser.getRole()))
//            throw new RuntimeException("No sufficient Access for this operation");
        List<AnnualAPY> list;
        list = apyRepository.findAll();
        return ResponseEntity.ok().body(list);
    }
}
