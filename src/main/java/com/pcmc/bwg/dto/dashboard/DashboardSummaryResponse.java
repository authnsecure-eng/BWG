package com.pcmc.bwg.dto.dashboard;

import java.util.List;

public class DashboardSummaryResponse {

    private final long totalApplications;
    private final long bwgCount;
    private final long approved;
    private final long rejected;
    private final List<LabelCount> categoryBreakdown;
    private final List<LabelCount> zoneBreakdown;
    private final List<LabelCount> wardBreakdown;
    private final List<ZoneWardRow> zoneWardBreakdown;
    private final WasteSummary wasteSummary;

    public DashboardSummaryResponse(long totalApplications, long bwgCount, long approved, long rejected,
                                     List<LabelCount> categoryBreakdown, List<LabelCount> zoneBreakdown,
                                     List<LabelCount> wardBreakdown, List<ZoneWardRow> zoneWardBreakdown,
                                     WasteSummary wasteSummary) {
        this.totalApplications = totalApplications;
        this.bwgCount = bwgCount;
        this.approved = approved;
        this.rejected = rejected;
        this.categoryBreakdown = categoryBreakdown;
        this.zoneBreakdown = zoneBreakdown;
        this.wardBreakdown = wardBreakdown;
        this.zoneWardBreakdown = zoneWardBreakdown;
        this.wasteSummary = wasteSummary;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public long getBwgCount() {
        return bwgCount;
    }

    public long getApproved() {
        return approved;
    }

    public long getRejected() {
        return rejected;
    }

    public List<LabelCount> getCategoryBreakdown() {
        return categoryBreakdown;
    }

    public List<LabelCount> getZoneBreakdown() {
        return zoneBreakdown;
    }

    public List<LabelCount> getWardBreakdown() {
        return wardBreakdown;
    }

    public List<ZoneWardRow> getZoneWardBreakdown() {
        return zoneWardBreakdown;
    }

    public WasteSummary getWasteSummary() {
        return wasteSummary;
    }
}
