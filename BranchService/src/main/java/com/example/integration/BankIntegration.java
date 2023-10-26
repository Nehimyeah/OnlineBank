    package com.example.integration;

    import com.example.domain.Branch;
    import com.example.dto.LoanResponseDto;
    import com.example.dto.ResponseAccountInfo;
    import lombok.RequiredArgsConstructor;
    import org.springframework.core.ParameterizedTypeReference;
    import org.springframework.http.*;
    import org.springframework.stereotype.Component;
    import org.springframework.web.client.RestTemplate;

    import java.util.Collections;
    import java.util.List;

    @Component
    @RequiredArgsConstructor
    public class BankIntegration {

        private final RestTemplate restTemplate;

        public ResponseAccountInfo getAccountNumber(Long id, String token) {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            String bankUrl = "http://localhost:8181/account/list/" + id;

            ResponseEntity<ResponseAccountInfo> response = restTemplate.exchange(
                    bankUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    });

            return response.getBody();
        }

        public LoanResponseDto getAllLoansByBranch(Long id, String token){

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            String bankUrl = "http://localhost:8181/account/loan/" + id;

            ResponseEntity<LoanResponseDto> response = restTemplate.exchange(
                    bankUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    });

           return response.getBody();
        }
    }
