package dev.steady.steady.dto.request;

import dev.steady.steady.domain.*;
import dev.steady.user.domain.User;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record SteadyCreateRequest(
        String name,
        String bio,
        SteadyType type,
        int recruitCount,
        SteadyMode steadyMode,
        LocalDate openingDate,
        LocalDate deadline,
        String title,
        String content,
        List<String> positions,
        List<String> stacks,
        List<String> questions
) {

    public Steady toEntity(User user, Promotion promotion, List<SteadyStack> steadyStacks) {
        return Steady.builder()
                .name(name)
                .bio(bio)
                .type(type)
                .recruitCount(recruitCount)
                .steadyMode(steadyMode)
                .openingDate(openingDate)
                .deadline(deadline)
                .title(title)
                .content(content)
                .user(user)
                .promotion(promotion)
                .steadyStacks(steadyStacks)
                .build();
    }

}
