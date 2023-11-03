package dev.steady.steady.dto;

import dev.steady.steady.dto.request.SteadySearchRequest;

import java.util.Arrays;
import java.util.List;

public record SearchKeywordDto(
        List<String> stacks,
        String mode,
        List<String> positions,
        boolean like
) {

    public static SearchKeywordDto create(SteadySearchRequest request) {
        List<String> stackArr = Arrays.stream(request.stack().split(",")).toList();
        String mode = request.mode();
        List<String> positionArr = Arrays.stream(request.position().split(",")).toList();
        boolean like = false;
        if (request.like().equals("true")) {
            like = true;
        }
        return new SearchKeywordDto(stackArr, mode, positionArr, like);
    }

}


