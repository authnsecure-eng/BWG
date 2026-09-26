package com.pcmc.bwg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Day-wise waste weighment for a BwgSurvey, mirroring the mobile app's
 * SurveyWasteVisit. Populated by SurveyIngestService when a survey (or an
 * updated day's visit) arrives from the mobile app backend.
 */
@Entity
@Table(name = "bwg_survey_waste_visit",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bwg_survey_id", "day_number"}))
public class BwgSurveyWasteVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bwg_survey_id", nullable = false)
    private BwgSurvey bwgSurvey;

    @Column(name = "day_number", nullable = false)
    private Integer dayNumber;

    @Column(name = "visit_date", length = 50)
    private String visitDate;

    @Column(name = "wet_waste_kg", precision = 12, scale = 2)
    private BigDecimal wetWasteKg = BigDecimal.ZERO;

    @Column(name = "dry_waste_kg", precision = 12, scale = 2)
    private BigDecimal dryWasteKg = BigDecimal.ZERO;

    @Column(name = "garden_waste_kg", precision = 12, scale = 2)
    private BigDecimal gardenWasteKg = BigDecimal.ZERO;

    @Column(name = "total_waste_kg", precision = 12, scale = 2)
    private BigDecimal totalWasteKg = BigDecimal.ZERO;

    @Column(name = "wet_waste_photo_path", length = 500)
    private String wetWastePhotoPath;

    @Column(name = "dry_waste_photo_path", length = 500)
    private String dryWastePhotoPath;

    @Column(name = "garden_waste_photo_path", length = 500)
    private String gardenWastePhotoPath;

    @Column(name = "weighing_scale_photo_path", length = 500)
    private String weighingScalePhotoPath;

    @Column(name = "handover_area_photo_path", length = 500)
    private String handoverAreaPhotoPath;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }

    public BwgSurvey getBwgSurvey() { return bwgSurvey; }
    public void setBwgSurvey(BwgSurvey bwgSurvey) { this.bwgSurvey = bwgSurvey; }

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

    public String getWetWastePhotoPath() { return wetWastePhotoPath; }
    public void setWetWastePhotoPath(String wetWastePhotoPath) { this.wetWastePhotoPath = wetWastePhotoPath; }

    public String getDryWastePhotoPath() { return dryWastePhotoPath; }
    public void setDryWastePhotoPath(String dryWastePhotoPath) { this.dryWastePhotoPath = dryWastePhotoPath; }

    public String getGardenWastePhotoPath() { return gardenWastePhotoPath; }
    public void setGardenWastePhotoPath(String gardenWastePhotoPath) { this.gardenWastePhotoPath = gardenWastePhotoPath; }

    public String getWeighingScalePhotoPath() { return weighingScalePhotoPath; }
    public void setWeighingScalePhotoPath(String weighingScalePhotoPath) { this.weighingScalePhotoPath = weighingScalePhotoPath; }

    public String getHandoverAreaPhotoPath() { return handoverAreaPhotoPath; }
    public void setHandoverAreaPhotoPath(String handoverAreaPhotoPath) { this.handoverAreaPhotoPath = handoverAreaPhotoPath; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
