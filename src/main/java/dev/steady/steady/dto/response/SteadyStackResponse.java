package dev.steady.steady.dto.response;

import dev.steady.steady.domain.SteadyStack;
import dev.steady.user.domain.Stack;

public record SteadyStackResponse(
        Long id,
        String name,
        String imageUrl
) {

    public static SteadyStackResponse from(SteadyStack steadyStack) {
        Stack stack = steadyStack.getStack();
        return new SteadyStackResponse(stack.getId(), stack.getName(), stack.getImageUrl());
    }

}
