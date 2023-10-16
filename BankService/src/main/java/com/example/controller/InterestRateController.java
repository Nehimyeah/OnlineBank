package com.example.controller;

import com.example.dto.request.AnnualInterestRequest;
import com.example.services.AnnualAPRService;
import com.example.services.AnnualAPYService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interestrate")
@CrossOrigin(
        origins = "http://127.0.0.1:5173/",
        allowedHeaders = "*", maxAge = 3600,
        allowCredentials = "true"
)
public class InterestRateController {
    @Autowired
    AnnualAPYService apyService;
    @Autowired
    AnnualAPRService aprService;
    @PostMapping("/apr")
    public ResponseEntity<?> createApr(@RequestBody AnnualInterestRequest annualInterestRequest, @RequestHeader("Authorization") String token){
        return aprService.create(annualInterestRequest,token);
    }
    @PostMapping("/apy")
    public ResponseEntity<?> createApy(@RequestBody AnnualInterestRequest annualInterestRequest,@RequestHeader("Authorization") String token){
        return apyService.create(annualInterestRequest,token);
    }
    @GetMapping("/aprlist")
    public ResponseEntity<?> getAPRList(){
        return aprService.getAPRList();
    }
    @GetMapping("/apylist")
    public ResponseEntity<?> getAPYList(){
        return apyService.getAPYList();
    }
}
