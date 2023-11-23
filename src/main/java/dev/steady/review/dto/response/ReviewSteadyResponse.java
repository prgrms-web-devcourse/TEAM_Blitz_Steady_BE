package dev.steady.review.dto.response;

import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.domain.SteadyType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReviewSteadyResponse(
        Long steadyId,
        String name,
        SteadyMode steadyMode,
        SteadyType steadyType,
        int participants,
        LocalDate participatedAt,
        LocalDate finishedAt,
        LocalDate reviewClosedAt
) {

    public static ReviewSteadyResponse from(Steady steady, Participant reviewer) {
        return ReviewSteadyResponse.builder()
                .steadyId(steady.getId())
                .name(steady.getName())
                .steadyMode(steady.getSteadyMode())
                .steadyType(steady.getType())
                .participants(steady.getNumberOfParticipants())
                .participatedAt(reviewer.getCreatedAt().toLocalDate())
                .finishedAt(steady.getFinishedAt())
                .reviewClosedAt(steady.getFinishedAt().plusMonths(2L))
                .build();
    }

}
