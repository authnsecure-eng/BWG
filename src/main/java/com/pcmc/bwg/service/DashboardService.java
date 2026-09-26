package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.dashboard.DashboardSummaryResponse;
import com.pcmc.bwg.dto.dashboard.LabelCount;
import com.pcmc.bwg.dto.dashboard.WasteSummary;
import com.pcmc.bwg.entity.enums.SurveyStatus;
import com.pcmc.bwg.exception.BadRequestException;
import com.pcmc.bwg.repository.BwgSurveyRepository;
import com.pcmc.bwg.repository.SurveyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);

    private static final int TOP_WARDS_LIMIT = 5;

    private final BwgSurveyRepository bwgSurveyRepository;

    public DashboardService(BwgSurveyRepository bwgSurveyRepository) {
        this.bwgSurveyRepository = bwgSurveyRepository;
    }

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary(String search, String zone, String ward, String category,
                                                String status, LocalDate fromDate, LocalDate toDate) {
        log.info("START getSummary");
        try {
            SurveyFilter filter = buildFilter(search, zone, ward, category, status, fromDate, toDate);

            Map<SurveyStatus, Long> statusCounts = bwgSurveyRepository.countByStatus(filter);
            long total = statusCounts.values().stream().mapToLong(Long::longValue).sum();
            long approved = statusCounts.getOrDefault(SurveyStatus.COMPLETED, 0L);
            long rejected = statusCounts.getOrDefault(SurveyStatus.REJECTED, 0L);

            List<LabelCount> categoryBreakdown = bwgSurveyRepository.countGroupByCategory(filter);
            List<LabelCount> zoneBreakdown = bwgSurveyRepository.countGroupByZone(filter);
            List<LabelCount> wardBreakdown = bwgSurveyRepository.countGroupByWard(filter).stream()
                    .limit(TOP_WARDS_LIMIT)
                    .toList();
            WasteSummary wasteSummary = bwgSurveyRepository.wasteSummary(filter);

            DashboardSummaryResponse response = new DashboardSummaryResponse(total, total, approved, rejected,
                    categoryBreakdown, zoneBreakdown, wardBreakdown, bwgSurveyRepository.zoneWardBreakdown(filter),
                    wasteSummary);
            log.info("SUCCESS getSummary total={}", total);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR getSummary - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    private SurveyFilter buildFilter(String search, String zone, String ward, String category, String status,
                                      LocalDate fromDate, LocalDate toDate) {
        SurveyStatus statusFilter = StringUtils.hasText(status) ? parseStatus(status) : null;
        OffsetDateTime fromDateTime = fromDate != null
                ? fromDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime() : null;
        OffsetDateTime toDateTime = toDate != null
                ? toDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toOffsetDateTime() : null;
        return new SurveyFilter(search, zone, ward, category, statusFilter, fromDateTime, toDateTime);
    }

    private SurveyStatus parseStatus(String status) {
        try {
            return SurveyStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid status: " + status);
        }
    }
}
