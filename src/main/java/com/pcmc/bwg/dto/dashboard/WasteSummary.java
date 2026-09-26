package com.pcmc.bwg.dto.dashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Waste-generation totals aggregated (server-side, SUM) across BWG surveys
 * matching the dashboard's current filter. Values come from each survey's
 * declared per-day waste fields (captured at registration time) - PCMC's
 * backend does not yet track actual daily pickup/collection, so this is an
 * estimate of generation, not a live collection log.
 *
 * eWasteKgDay does not exist on the survey form (e-waste is declared per
 * month), so its monthly total is converted to a daily-equivalent by
 * dividing by 30 for a like-for-like comparison with the other categories.
 */
public class WasteSummary {

    private static final BigDecimal KG_PER_MT = BigDecimal.valueOf(1000);
    private static final BigDecimal DAYS_PER_MONTH = BigDecimal.valueOf(30);
    private static final int SCALE = 2;

    private final BigDecimal wetWasteMt;
    private final BigDecimal dryWasteMt;
    private final BigDecimal sanitaryWasteMt;
    private final BigDecimal eWasteMt;
    private final BigDecimal totalWasteMt;

    public WasteSummary(BigDecimal wetWasteKgDay, BigDecimal dryWasteKgDay, BigDecimal sanitaryWasteKgDay,
                         BigDecimal eWasteKgMonth) {
        this.wetWasteMt = toMt(wetWasteKgDay);
        this.dryWasteMt = toMt(dryWasteKgDay);
        this.sanitaryWasteMt = toMt(sanitaryWasteKgDay);
        this.eWasteMt = eWasteKgMonth == null
                ? BigDecimal.ZERO.setScale(SCALE)
                : toMt(eWasteKgMonth.divide(DAYS_PER_MONTH, 4, RoundingMode.HALF_UP));
        this.totalWasteMt = this.wetWasteMt.add(this.dryWasteMt).add(this.sanitaryWasteMt).add(this.eWasteMt);
    }

    private static BigDecimal toMt(BigDecimal kg) {
        if (kg == null) {
            return BigDecimal.ZERO.setScale(SCALE);
        }
        return kg.divide(KG_PER_MT, SCALE, RoundingMode.HALF_UP);
    }

    public BigDecimal getWetWasteMt() {
        return wetWasteMt;
    }

    public BigDecimal getDryWasteMt() {
        return dryWasteMt;
    }

    public BigDecimal getSanitaryWasteMt() {
        return sanitaryWasteMt;
    }

    public BigDecimal getEWasteMt() {
        return eWasteMt;
    }

    public BigDecimal getTotalWasteMt() {
        return totalWasteMt;
    }
}
