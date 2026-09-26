package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.report.AdminReportResponse;
import com.pcmc.bwg.dto.report.AdminSurveyDetailResponse;
import com.pcmc.bwg.dto.report.PagedResponse;
import com.pcmc.bwg.dto.report.ReportStatusUpdateRequest;
import com.pcmc.bwg.service.AdminReportService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    private static final Logger log = LoggerFactory.getLogger(AdminReportController.class);

    private final AdminReportService adminReportService;

    public AdminReportController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    @GetMapping("/surveys")
    public ResponseEntity<PagedResponse<AdminReportResponse>> getReports(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String ward,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("START getReports page={} size={}", page, size);
        try {
            ResponseEntity<PagedResponse<AdminReportResponse>> response = ResponseEntity.ok(adminReportService.getReports(
                    search, zone, ward, category, status, fromDate, toDate, page, size));
            log.info("SUCCESS getReports page={} size={}", page, size);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR getReports page={} size={} - {}", page, size, ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/surveys/{id}")
    public ResponseEntity<AdminSurveyDetailResponse> getSurveyDetail(@PathVariable Long id) {
        log.info("START getSurveyDetail id={}", id);
        try {
            ResponseEntity<AdminSurveyDetailResponse> response = ResponseEntity.ok(adminReportService.getSurveyDetail(id));
            log.info("SUCCESS getSurveyDetail id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR getSurveyDetail id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping("/surveys/{id}/status")
    public ResponseEntity<AdminSurveyDetailResponse> updateStatus(@PathVariable Long id,
                                                                    @Valid @RequestBody ReportStatusUpdateRequest request) {
        log.info("START updateStatus id={}", id);
        try {
            ResponseEntity<AdminSurveyDetailResponse> response =
                    ResponseEntity.ok(adminReportService.updateStatus(id, request));
            log.info("SUCCESS updateStatus id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR updateStatus id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/surveys/export")
    public ResponseEntity<byte[]> exportSurveys(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String ward,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        log.info("START exportSurveys");
        try {
            byte[] excel = adminReportService.exportToExcel(search, zone, ward, category, status, fromDate, toDate);

            String filename = "bwg-survey-report-" + DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now()) + ".xlsx";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename(filename)
                    .build());

            ResponseEntity<byte[]> response = ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(excel);
            log.info("SUCCESS exportSurveys bytes={}", excel.length);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR exportSurveys - {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
