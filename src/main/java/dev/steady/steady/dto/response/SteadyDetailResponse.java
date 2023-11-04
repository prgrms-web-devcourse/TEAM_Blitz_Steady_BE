package dev.steady.steady.dto.response;

import dev.steady.steady.domain.ScheduledPeriod;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.domain.SteadyPosition;
import dev.steady.steady.domain.SteadyStatus;
import dev.steady.steady.domain.SteadyType;

import java.time.LocalDate;
import java.util.List;

public record SteadyDetailResponse(
        Long id,
        LeaderResponse leaderResponse,
        String name,
        String bio,
        SteadyType type,
        SteadyStatus status,
        String participantLimit,
        String numberOfParticipants,
        SteadyMode steadyMode,
        ScheduledPeriod scheduledPeriod,
        LocalDate deadline,
        String title,
        String content,
        List<String> positions,
        // TODO: 2023/11/02 position 도 아이디 같이 보내주기 (새로운 dto로 감싸기)
        List<SteadyStackResponse> stacks,
        boolean isLeader,
        boolean isSubmittedUser
) {

    public static SteadyDetailResponse of(Steady steady, List<SteadyPosition> positions, boolean isLeader, boolean isSubmittedUser) {
        return new SteadyDetailResponse(
                steady.getId(),
                LeaderResponse.from(steady.getParticipants().getLeader()),
                steady.getName(),
                steady.getTitle(),
                steady.getType(),
                steady.getStatus(),
                String.valueOf(steady.getParticipantLimit()),
                String.valueOf(steady.getNumberOfParticipants()),
                steady.getSteadyMode(),
                steady.getScheduledPeriod(),
                steady.getDeadline(),
                steady.getTitle(),
                steady.getContent(),
                positions.stream()
                        .map(position -> position.getPosition().getName())
                        .toList(),
                steady.getSteadyStacks().stream()
                        .map(SteadyStackResponse::from)
                        .toList(),
                isLeader,
                isSubmittedUser
        );
    }

}
