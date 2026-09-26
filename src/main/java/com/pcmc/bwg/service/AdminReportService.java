package com.pcmc.bwg.service;

import com.pcmc.bwg.dto.report.AdminReportResponse;
import com.pcmc.bwg.dto.report.AdminSurveyDetailResponse;
import com.pcmc.bwg.dto.report.PagedResponse;
import com.pcmc.bwg.dto.report.ReportStatusUpdateRequest;
import com.pcmc.bwg.entity.BwgSurvey;
import com.pcmc.bwg.entity.enums.SurveyStatus;
import com.pcmc.bwg.exception.BadRequestException;
import com.pcmc.bwg.exception.ResourceNotFoundException;
import com.pcmc.bwg.repository.BwgSurveyRepository;
import com.pcmc.bwg.repository.SurveyFilter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminReportService {

    private static final Logger log = LoggerFactory.getLogger(AdminReportService.class);

    private static final String[] EXPORT_HEADERS = {
            "BWG ID", "BWG Name", "Contact Person", "Mobile No", "Zone", "Ward", "Category", "Status", "Submitted Date"
    };

    private static final DateTimeFormatter EXPORT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // Character-width estimates per column, used for fixed column sizing instead of
    // Sheet#autoSizeColumn - autoSizeColumn measures every cell's text with AWT Font
    // metrics, which takes 10+ seconds once row counts reach the thousands.
    private static final int[] EXPORT_COLUMN_WIDTH_CHARS = {16, 30, 22, 14, 18, 12, 28, 12, 18};

    private final BwgSurveyRepository bwgSurveyRepository;

    public AdminReportService(BwgSurveyRepository bwgSurveyRepository) {
        this.bwgSurveyRepository = bwgSurveyRepository;
    }

    @Transactional(readOnly = true)
    public PagedResponse<AdminReportResponse> getReports(String search, String zone, String ward, String category,
                                                           String status, LocalDate fromDate, LocalDate toDate,
                                                           int page, int size) {
        log.info("START getReports page={} size={}", page, size);
        try {
            if (page < 0) {
                throw new BadRequestException("page must not be negative");
            }
            if (size <= 0) {
                throw new BadRequestException("size must be greater than zero");
            }

            SurveyFilter filter = buildFilter(search, zone, ward, category, status, fromDate, toDate);
            Pageable pageable = PageRequest.of(page, size);
            Page<AdminReportResponse> result = bwgSurveyRepository.search(filter, pageable);
            log.info("SUCCESS getReports page={} size={} totalElements={}", page, size, result.getTotalElements());
            return PagedResponse.of(result);
        } catch (RuntimeException ex) {
            log.error("ERROR getReports page={} size={} - {}", page, size, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public AdminSurveyDetailResponse getSurveyDetail(Long id) {
        log.info("START getSurveyDetail id={}", id);
        try {
            AdminSurveyDetailResponse response = AdminSurveyDetailResponse.from(findById(id));
            log.info("SUCCESS getSurveyDetail id={}", id);
            return response;
        } catch (RuntimeException ex) {
            log.error("ERROR getSurveyDetail id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional
    public AdminSurveyDetailResponse updateStatus(Long id, ReportStatusUpdateRequest request) {
        log.info("START updateStatus id={}", id);
        try {
            SurveyStatus newStatus = parseStatus(request.getStatus());
            BwgSurvey survey = findById(id);
            survey.setStatus(newStatus);
            survey.setUpdatedAt(OffsetDateTime.now());
            survey = bwgSurveyRepository.save(survey);
            log.info("Survey status updated, surveyId: {}, status: {}", survey.getId(), newStatus);
            log.info("SUCCESS updateStatus id={}", id);
            return AdminSurveyDetailResponse.from(survey);
        } catch (RuntimeException ex) {
            log.error("ERROR updateStatus id={} - {}", id, ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public byte[] exportToExcel(String search, String zone, String ward, String category, String status,
                                 LocalDate fromDate, LocalDate toDate) {
        log.info("START exportToExcel");
        try {
            SurveyFilter filter = buildFilter(search, zone, ward, category, status, fromDate, toDate);
            List<AdminReportResponse> rows = bwgSurveyRepository.searchForExport(filter);
            byte[] excel = buildWorkbook(rows);
            log.info("SUCCESS exportToExcel rows={} bytes={}", rows.size(), excel.length);
            return excel;
        } catch (RuntimeException ex) {
            log.error("ERROR exportToExcel - {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    private BwgSurvey findById(Long id) {
        return bwgSurveyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id " + id));
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

    private byte[] buildWorkbook(List<AdminReportResponse> rows) {
        // SXSSFWorkbook (streaming) instead of XSSFWorkbook: the latter keeps every
        // cell as an in-memory XML object and becomes the dominant cost (~5s+) once
        // row counts reach the thousands. SXSSF flushes rows to a temp file as it
        // goes, which is dramatically faster for write-once exports like this.
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("BWG Survey Reports");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < EXPORT_HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(EXPORT_HEADERS[i]);
            }

            int rowIndex = 1;
            for (AdminReportResponse r : rows) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(r.getApplicationNo());
                row.createCell(1).setCellValue(r.getBwgName());
                row.createCell(2).setCellValue(r.getContactPerson());
                row.createCell(3).setCellValue(r.getMobileNo());
                row.createCell(4).setCellValue(r.getZone());
                row.createCell(5).setCellValue(r.getWard());
                row.createCell(6).setCellValue(r.getCategory());
                row.createCell(7).setCellValue(r.getStatus() != null ? r.getStatus().name() : "");
                row.createCell(8).setCellValue(r.getSubmittedAt() != null ? EXPORT_DATE_FORMAT.format(r.getSubmittedAt()) : "");
            }

            for (int i = 0; i < EXPORT_COLUMN_WIDTH_CHARS.length; i++) {
                sheet.setColumnWidth(i, (EXPORT_COLUMN_WIDTH_CHARS[i] + 2) * 256);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to generate Excel export", ex);
        } finally {
            workbook.dispose();
        }
    }
}
