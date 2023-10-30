package dev.steady.steady.dto.request;

import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.domain.SteadyStatus;
import dev.steady.steady.domain.SteadyType;

import java.time.LocalDate;
import java.util.List;

public record SteadyUpdateRequest(
        String name,
        String bio,
        SteadyType type,
        SteadyStatus status,
        int participantLimit,
        SteadyMode steadyMode,
        LocalDate openingDate,
        LocalDate deadline,
        String title,
        String content,
        List<Long> positions,
        List<Long> stacks
) {

}
