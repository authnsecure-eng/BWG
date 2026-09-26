package com.pcmc.bwg.repository;

import com.pcmc.bwg.entity.enums.SurveyStatus;

import java.time.OffsetDateTime;

public record SurveyFilter(
        String search,
        String zone,
        String ward,
        String category,
        SurveyStatus status,
        OffsetDateTime fromDateTime,
        OffsetDateTime toDateTime
) {
}
