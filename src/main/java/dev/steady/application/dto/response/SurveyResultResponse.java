package dev.steady.application.dto.response;

import dev.steady.application.domain.SurveyResult;

public record SurveyResultResponse(String question, String answer) {

    public static SurveyResultResponse from(SurveyResult surveyResult) {
        return new SurveyResultResponse(surveyResult.getQuestion(), surveyResult.getAnswer());
    }

}
