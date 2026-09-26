package com.pcmc.bwg.dto.dashboard;

public class LabelCount {

    private final String label;
    private final long count;

    public LabelCount(String label, Long count) {
        this.label = label;
        this.count = count == null ? 0 : count;
    }

    public String getLabel() {
        return label;
    }

    public long getCount() {
        return count;
    }
}
