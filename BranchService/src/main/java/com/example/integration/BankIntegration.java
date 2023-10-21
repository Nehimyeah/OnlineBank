package com.example.integration;

import com.example.dto.ResponseAccountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class BankIntegration {

    private final RestTemplate restTemplate;

    public ResponseAccountInfo getAccountNumber(Long id, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<?> entity = new HttpEntity<>(id, headers);

        String bankUrl = "http://localhost:8181/account/list";

        return restTemplate.exchange(
                bankUrl,
                HttpMethod.GET,
                entity,
                ResponseAccountInfo.class
        ).getBody();
    }

}
