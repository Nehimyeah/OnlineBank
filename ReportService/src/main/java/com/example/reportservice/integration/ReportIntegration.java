package com.example.reportservice.integration;

import com.example.reportservice.dto.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(url = "http://localhost:8181/transaction/list", value = "bank-service")
public interface ReportIntegration {

    @RequestMapping(method = RequestMethod.GET, value = "{accountNumber}")
    List<Transaction> getTransactions(@PathVariable String accountNumber);


}
