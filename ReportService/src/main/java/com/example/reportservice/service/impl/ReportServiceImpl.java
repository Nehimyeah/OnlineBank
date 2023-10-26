package com.example.reportservice.service.impl;

import com.example.reportservice.dto.Transaction;
import com.example.reportservice.integration.ReportIntegration;
import com.example.reportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportIntegration reportIntegration;

    @Override
    public byte[] getStatements(String token, String accountNumber) {
        try{
            File file = ResourceUtils.getFile("classpath:statement.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            List<Transaction> transactions = reportIntegration.getTransactions(accountNumber);
            System.out.println(transactions);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transactions);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("accountNumber", accountNumber);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            byte[] reportContent;
            reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
            return reportContent;
        } catch (FileNotFoundException | JRException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Transaction> getTransaction(String accountNumber) {
        return reportIntegration.getTransactions(accountNumber);
    }
}
