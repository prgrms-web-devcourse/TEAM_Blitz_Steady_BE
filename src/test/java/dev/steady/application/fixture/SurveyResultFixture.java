package dev.steady.application.fixture;

import dev.steady.application.dto.request.SurveyResultRequest;

import java.util.List;

public class SurveyResultFixture {

    public static List<SurveyResultRequest> createSurveyResultRequests() {
        return List.of(
                new SurveyResultRequest("질문1", "답변1"),
                new SurveyResultRequest("질문2", "답변2"),
                new SurveyResultRequest("질문3", "답변3")
        );
    }

}
