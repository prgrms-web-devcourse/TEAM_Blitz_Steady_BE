package dev.steady.steady.dto.response;


import dev.steady.steady.domain.SteadyQuestion;

import java.util.List;

public record SteadyQuestionsResponse(
        List<SteadyQuestionResponse> steadyQuestions
) {

    public static SteadyQuestionsResponse from(List<SteadyQuestion> steadyQuestions) {
        List<SteadyQuestionResponse> response = steadyQuestions.stream()
                .map(SteadyQuestionResponse::from)
                .toList();
        return new SteadyQuestionsResponse(response);
    }

}
