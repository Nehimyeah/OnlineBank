package com.example.reportservice.controller;

import com.example.reportservice.dto.Transaction;
import com.example.reportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "http://127.0.0.1:5173/",
        allowedHeaders = "*", maxAge = 3600,
        allowCredentials = "true"
)
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/bank-statement/{accountNumber}")
    public ResponseEntity<Resource> getItemReport(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable String accountNumber
    ) throws JRException, RuntimeException {
        byte[] reportContent = reportService.getStatements(token, accountNumber);
        ByteArrayResource resource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("bank-statement.pdf")
                                .build().toString())
                .body(resource);
    }

    @GetMapping("/{accountNumber}")
    List<Transaction> getTransactions(@PathVariable String accountNumber) {
        return reportService.getTransaction(accountNumber);
    }
}
