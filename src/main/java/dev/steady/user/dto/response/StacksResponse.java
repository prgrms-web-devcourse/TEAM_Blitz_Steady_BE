package dev.steady.user.dto.response;

import dev.steady.user.domain.Stack;

import java.util.List;

public record StacksResponse(
        List<StackResponse> stacks
) {

    public static StacksResponse from(List<Stack> stacks) {
        List<StackResponse> responses = stacks.stream()
                .map(StackResponse::from)
                .toList();

        return new StacksResponse(responses);
    }

}
