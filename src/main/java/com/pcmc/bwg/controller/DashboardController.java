package com.pcmc.bwg.controller;

import com.pcmc.bwg.dto.dashboard.DashboardSummaryResponse;
import com.pcmc.bwg.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getSummary(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String ward,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        log.info("START getSummary");
        try {
            ResponseEntity<DashboardSummaryResponse> response =
                    ResponseEntity.ok(dashboardService.getSummary(search, zone, ward, category, status, fromDate, toDate));
            log.info("SUCCESS getSummary");
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR getSummary - {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
