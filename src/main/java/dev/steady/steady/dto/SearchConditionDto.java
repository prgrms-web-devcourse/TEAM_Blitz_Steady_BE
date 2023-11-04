package dev.steady.steady.dto;

import dev.steady.steady.dto.request.SteadySearchRequest;

import java.util.Arrays;
import java.util.List;

public record SearchConditionDto(
        String mode,
        List<String> stacks,
        List<String> positions,
        boolean like
) {

    public static SearchConditionDto of(SteadySearchRequest request) {
        String mode = request.mode();
        List<String> stackArr = Arrays.stream(request.stack().split(",")).toList();
        List<String> positionArr = Arrays.stream(request.position().split(",")).toList();
        boolean like = false;
        if (request.like().equals("true")) {
            like = true;
        }
        return new SearchConditionDto(stackArr, mode, positionArr, like);
    }
    // TODO: 2023/11/03 request의 각 필드들이 null인지 아닌지 체크

}


