package dev.steady.user.dto.response;

import dev.steady.user.domain.Stack;

import java.util.List;

public record StackResponses(
        List<StackResponse> stacks
) {

    public static StackResponses from(List<Stack> stacks) {
        List<StackResponse> responses = stacks.stream()
                .map(StackResponse::from)
                .toList();

        return new StackResponses(responses);
    }

}
