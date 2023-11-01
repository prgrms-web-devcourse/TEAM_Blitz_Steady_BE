package dev.steady.steady.dto.request;

import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.domain.SteadyStatus;
import dev.steady.steady.domain.SteadyType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record SteadyUpdateRequest(
        String name,
        String bio,
        SteadyType type,
        SteadyStatus status,
        int participantLimit,
        SteadyMode steadyMode,
        String scheduledPeriod,
        LocalDate deadline,
        String title,
        String content,
        List<Long> positions,
        List<Long> stacks
) {

}
