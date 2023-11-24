package dev.steady.steady.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SteadyQuestionUpdateRequest(
        @NotNull(message = "NULL은 올 수 없습니다.")
        List<String> questions
) {
}
