package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.BwgSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BwgSurveyRepository extends JpaRepository<BwgSurvey, Long>, BwgSurveyRepositoryCustom {
    Optional<BwgSurvey> findByMobileSurveyId(String mobileSurveyId);
    boolean existsByApplicationNo(String applicationNo);
}
