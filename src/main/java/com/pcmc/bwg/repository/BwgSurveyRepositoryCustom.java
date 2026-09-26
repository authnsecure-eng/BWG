package com.pcmc.bwg.repository;

import com.pcmc.bwg.dto.dashboard.LabelCount;
import com.pcmc.bwg.dto.dashboard.WasteSummary;
import com.pcmc.bwg.dto.dashboard.ZoneWardRow;
import com.pcmc.bwg.dto.report.AdminReportResponse;
import com.pcmc.bwg.entity.enums.SurveyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BwgSurveyRepositoryCustom {

    Page<AdminReportResponse> search(SurveyFilter filter, Pageable pageable);

    List<AdminReportResponse> searchForExport(SurveyFilter filter);

    Map<SurveyStatus, Long> countByStatus(SurveyFilter filter);

    List<LabelCount> countGroupByCategory(SurveyFilter filter);

    List<LabelCount> countGroupByZone(SurveyFilter filter);

    List<LabelCount> countGroupByWard(SurveyFilter filter);

    List<ZoneWardRow> zoneWardBreakdown(SurveyFilter filter);

    WasteSummary wasteSummary(SurveyFilter filter);
}
