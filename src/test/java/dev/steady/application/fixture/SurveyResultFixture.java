package dev.steady.application.fixture;

import dev.steady.application.dto.request.ApplicationUpdateAnswerRequest;
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

    public static ApplicationUpdateAnswerRequest createAnswers() {
        return new ApplicationUpdateAnswerRequest(List.of(
                "답변100", "답변101", "답변102"
        ));
    }

}
