package com.pcmc.bwg.dto.dashboard;

public class ZoneWardRow {

    private final String zone;
    private final long totalWards;
    private final long applications;

    public ZoneWardRow(String zone, Long totalWards, Long applications) {
        this.zone = zone;
        this.totalWards = totalWards == null ? 0 : totalWards;
        this.applications = applications == null ? 0 : applications;
    }

    public String getZone() {
        return zone;
    }

    public long getTotalWards() {
        return totalWards;
    }

    public long getApplications() {
        return applications;
    }
}
