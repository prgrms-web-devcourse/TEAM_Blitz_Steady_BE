package dev.steady.steady.fixture;

import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.domain.SteadyType;
import dev.steady.steady.dto.request.SteadyCreateRequest;

import java.time.LocalDate;
import java.util.List;

public class SteadyFixtures {

    public static SteadyCreateRequest createSteadyRequest(Long stackId, Long positionId) {
        return SteadyCreateRequest.builder()
                .name("테스트 스테디")
                .bio("무슨 스터디")
                .type(SteadyType.STUDY)
                .participantLimit(6)
                .steadyMode(SteadyMode.ONLINE)
                .openingDate(LocalDate.now().plusDays(7))
                .deadline(LocalDate.now().plusDays(14))
                .title("스테디원 모집합니다")
                .content("많관부")
                .positions(List.of(positionId))
                .stacks(List.of(stackId))
                .questions(List.of("1번 질문", "2번 질문", "3번 질문"))
                .build();
    }

}
