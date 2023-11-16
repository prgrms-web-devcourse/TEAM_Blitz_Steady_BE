package dev.steady.steady.dto.response;


import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyQuestion;

import java.util.List;

public record SteadyQuestionsResponse(
        String steadyName,
        List<SteadyQuestionResponse> steadyQuestions
) {

    public static SteadyQuestionsResponse of(Steady steady, List<SteadyQuestion> steadyQuestions) {
        List<SteadyQuestionResponse> response = steadyQuestions.stream()
                .map(SteadyQuestionResponse::from)
                .toList();
        return new SteadyQuestionsResponse(steady.getName(), response);
    }

}
