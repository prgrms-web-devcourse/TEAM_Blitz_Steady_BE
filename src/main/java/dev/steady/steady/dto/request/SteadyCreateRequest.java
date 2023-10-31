package dev.steady.steady.dto.request;

import dev.steady.steady.domain.Promotion;
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
        LocalDate openingDate,
        LocalDate deadline,
        String title,
        String content,
        List<Long> positions,
        List<Long> stacks,
        List<String> questions
) {

    public Steady toEntity(User user, Promotion promotion, List<Stack> stacks) {
        return Steady.builder()
                .name(name)
                .bio(bio)
                .type(type)
                .participantLimit(participantLimit)
                .steadyMode(steadyMode)
                .openingDate(openingDate)
                .deadline(deadline)
                .title(title)
                .content(content)
                .user(user)
                .promotion(promotion)
                .stacks(stacks)
                .build();
    }

}
