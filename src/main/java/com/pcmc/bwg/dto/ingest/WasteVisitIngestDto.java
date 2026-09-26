package com.pcmc.bwg.dto.ingest;

import java.math.BigDecimal;

/** One day's waste weighment, as sent by the mobile app backend. */
public class WasteVisitIngestDto {

    private Integer dayNumber;
    private String visitDate;
    private BigDecimal wetWasteKg;
    private BigDecimal dryWasteKg;
    private BigDecimal gardenWasteKg;
    private BigDecimal totalWasteKg;
    private String wetWastePhotoUrl;
    private String dryWastePhotoUrl;
    private String gardenWastePhotoUrl;
    private String weighingScalePhotoUrl;
    private String handoverAreaPhotoUrl;
    private Boolean isCompleted;

    public Integer getDayNumber() { return dayNumber; }
    public void setDayNumber(Integer dayNumber) { this.dayNumber = dayNumber; }

    public String getVisitDate() { return visitDate; }
    public void setVisitDate(String visitDate) { this.visitDate = visitDate; }

    public BigDecimal getWetWasteKg() { return wetWasteKg; }
    public void setWetWasteKg(BigDecimal wetWasteKg) { this.wetWasteKg = wetWasteKg; }

    public BigDecimal getDryWasteKg() { return dryWasteKg; }
    public void setDryWasteKg(BigDecimal dryWasteKg) { this.dryWasteKg = dryWasteKg; }

    public BigDecimal getGardenWasteKg() { return gardenWasteKg; }
    public void setGardenWasteKg(BigDecimal gardenWasteKg) { this.gardenWasteKg = gardenWasteKg; }

    public BigDecimal getTotalWasteKg() { return totalWasteKg; }
    public void setTotalWasteKg(BigDecimal totalWasteKg) { this.totalWasteKg = totalWasteKg; }

    public String getWetWastePhotoUrl() { return wetWastePhotoUrl; }
    public void setWetWastePhotoUrl(String wetWastePhotoUrl) { this.wetWastePhotoUrl = wetWastePhotoUrl; }

    public String getDryWastePhotoUrl() { return dryWastePhotoUrl; }
    public void setDryWastePhotoUrl(String dryWastePhotoUrl) { this.dryWastePhotoUrl = dryWastePhotoUrl; }

    public String getGardenWastePhotoUrl() { return gardenWastePhotoUrl; }
    public void setGardenWastePhotoUrl(String gardenWastePhotoUrl) { this.gardenWastePhotoUrl = gardenWastePhotoUrl; }

    public String getWeighingScalePhotoUrl() { return weighingScalePhotoUrl; }
    public void setWeighingScalePhotoUrl(String weighingScalePhotoUrl) { this.weighingScalePhotoUrl = weighingScalePhotoUrl; }

    public String getHandoverAreaPhotoUrl() { return handoverAreaPhotoUrl; }
    public void setHandoverAreaPhotoUrl(String handoverAreaPhotoUrl) { this.handoverAreaPhotoUrl = handoverAreaPhotoUrl; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
}
