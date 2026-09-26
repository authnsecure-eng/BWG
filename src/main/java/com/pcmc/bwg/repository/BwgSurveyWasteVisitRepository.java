package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.BwgSurveyWasteVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BwgSurveyWasteVisitRepository extends JpaRepository<BwgSurveyWasteVisit, Long> {
    Optional<BwgSurveyWasteVisit> findByBwgSurvey_IdAndDayNumber(Long bwgSurveyId, Integer dayNumber);
}
