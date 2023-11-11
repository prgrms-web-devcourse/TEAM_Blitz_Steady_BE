package dev.steady.user.dto.response;

import dev.steady.user.domain.Stack;

public record StackResponse(
        Long id,
        String name,
        String imageUrl
) {

    public static StackResponse from(Stack stack) {
        return new StackResponse(stack.getId(), stack.getName(), stack.getImageUrl());
    }

}
