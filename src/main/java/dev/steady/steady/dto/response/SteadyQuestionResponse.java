package dev.steady.steady.dto.response;


import dev.steady.steady.domain.SteadyQuestion;

public record SteadyQuestionResponse(
        Long id,
        String content,
        int sequence
) {

    public static SteadyQuestionResponse from(SteadyQuestion steadyQuestion) {
        return new SteadyQuestionResponse(steadyQuestion.getId(), steadyQuestion.getContent(), steadyQuestion.getSequence());
    }

}
