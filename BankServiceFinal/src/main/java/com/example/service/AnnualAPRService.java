package com.example.service;


import com.example.domain.AnnualAPR;
import com.example.dto.request.AnnualInterestRequest;
import com.example.repository.AnnualAPRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnnualAPRService {
    @Autowired
    AnnualAPRRepository aprRepository;

    public ResponseEntity<?> create(AnnualInterestRequest annualInterestRequest){
        try{
            AnnualAPR annualAPR = new AnnualAPR();
            annualAPR.setRateType(annualInterestRequest.getRateType());
            annualAPR.setYear(annualInterestRequest.getYear());
            annualAPR.setAnnualAPR(annualInterestRequest.getAnnualInterest());
            annualAPR.setMonths(annualInterestRequest.getMonths());
            annualAPR.setDescription(annualInterestRequest.getDescription());
            aprRepository.save(annualAPR);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("APR creation failed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("APR rate saved");
    }

    public double findById(long id) {
        return aprRepository.findById(id).get().getAnnualAPR();
    }

}
