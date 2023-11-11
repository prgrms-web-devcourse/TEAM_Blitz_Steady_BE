package dev.steady.steady.dto.request;

import java.util.List;

public record SteadyQuestionUpdateRequest(
        List<String> questions
) {
}
