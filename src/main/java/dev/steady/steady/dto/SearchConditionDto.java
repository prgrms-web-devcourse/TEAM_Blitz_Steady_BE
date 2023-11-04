package dev.steady.steady.dto;

import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.domain.SteadyStatus;
import dev.steady.steady.dto.request.SteadySearchRequest;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record SearchConditionDto(
        SteadyMode steadyMode,
        List<String> stacks,
        List<String> positions,
        SteadyStatus status,
        boolean like,
        String keyword
) {

    public static SearchConditionDto from(SteadySearchRequest request) {
        SteadyMode steadyMode = null;
        if (!request.steadyMode().equals("all")) {
            steadyMode = SteadyMode.from(request.steadyMode());
        }

        List<String> stackArr = new ArrayList<>();
        if (StringUtils.hasText(request.stack())) {
            stackArr = Arrays.stream(request.stack().split(",")).toList();
        }

        List<String> positionArr = new ArrayList<>();
        if (StringUtils.hasText(request.position())) {
            positionArr = Arrays.stream(request.position().split(",")).toList();
        }

        SteadyStatus status = null;
        if (!request.status().equals("all")) {
            status = SteadyStatus.from(request.status());
        }

        boolean like = false;
        if (request.like().equals("true")) {
            like = true;
        }
        // TODO: 2023-11-04 현재 상태를 유지하고 private 메서드로 분리할지, Service로 분리해야 할지?
        return new SearchConditionDto(steadyMode,
                stackArr,
                positionArr,
                status,
                like,
                request.keyword());
    }

}


