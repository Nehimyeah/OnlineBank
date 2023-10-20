package com.example.integration;

import com.example.dto.GetAccountInfo;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BankIntegration {

    private final RestTemplate restTemplate;

    public GetAccountInfo getAccountNumber() {

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);

        String bankUrl = "http://localhost:8181/account/list";

        return restTemplate.getForObject(bankUrl, GetAccountInfo.class);
    }

    private List<GetAccountInfo> getAccountInfoList(){

        return new ArrayList<>();
    }

}
