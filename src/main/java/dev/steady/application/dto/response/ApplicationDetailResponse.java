package dev.steady.application.dto.response;

import dev.steady.application.domain.SurveyResult;

import java.util.List;

public record ApplicationDetailResponse(List<SurveyResultResponse> surveys) {

    public static ApplicationDetailResponse from(List<SurveyResult> surveyResults) {
        return new ApplicationDetailResponse(
                surveyResults.stream()
                        .map(SurveyResultResponse::from)
                        .toList());
    }

}
