package dev.steady.steady.dto.request;

import dev.steady.steady.domain.ScheduledPeriod;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.domain.SteadyType;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record SteadyCreateRequest(
        String name,
        String bio,
        SteadyType type,
        int participantLimit,
        SteadyMode steadyMode,
        String scheduledPeriod,
        LocalDate deadline,
        String title,
        String content,
        List<Long> positions,
        List<Long> stacks,
        List<String> questions
) {

    public Steady toEntity(User user, List<Stack> stacks) {
        return Steady.builder()
                .name(name)
                .bio(bio)
                .type(type)
                .participantLimit(participantLimit)
                .steadyMode(steadyMode)
                .scheduledPeriod(ScheduledPeriod.valueOf(scheduledPeriod))
                .deadline(deadline)
                .title(title)
                .content(content)
                .user(user)
                .stacks(stacks)
                .build();
    }

}
