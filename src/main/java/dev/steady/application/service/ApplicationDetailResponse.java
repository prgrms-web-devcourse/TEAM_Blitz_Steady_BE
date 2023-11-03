package dev.steady.application.service;

import dev.steady.application.domain.SurveyResults;

import java.util.List;

public record ApplicationDetailResponse(List<SurveyResultResponse> surveys) {

    public static ApplicationDetailResponse from(SurveyResults surveyResults) {
        return new ApplicationDetailResponse(
                surveyResults.getValues().stream()
                        .map(SurveyResultResponse::from)
                        .toList());
    }

}
