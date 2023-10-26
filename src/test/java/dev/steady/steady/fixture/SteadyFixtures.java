package dev.steady.steady.fixture;

import dev.steady.steady.domain.*;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public class SteadyFixtures {

    public static SteadyCreateRequest createSteadyRequest() {
        return SteadyCreateRequest.builder()
                .name("테스트 스테디")
                .bio("무슨 스터디")
                .type(SteadyType.STUDY)
                .recruitCount(6)
                .steadyMode(SteadyMode.ONLINE)
                .openingDate(LocalDate.now().plusDays(7))
                .deadline(LocalDate.now().plusDays(14))
                .title("스테디원 모집합니다")
                .content("많관부")
                .positions(List.of("백엔드"))
                .stacks(List.of("Java"))
                .questions(List.of("1번 질문", "2번 질문", "3번 질문"))
                .build();
    }

    public static List<SteadyQuestion> createSteadyQuestions(List<String> questions, Steady steady) {
        return IntStream.range(0, questions.size())
                .mapToObj(index -> {
                    SteadyQuestion steadyQuestion = SteadyQuestion.builder()
                            .content(questions.get(index))
                            .sequence(index + 1)
                            .steady(steady)
                            .build();
                    ReflectionTestUtils.setField(steadyQuestion, "id", Long.valueOf(index + 1));
                    return steadyQuestion;
                })
                .toList();
    }

    public static Steady createSteady(SteadyCreateRequest request, Promotion promotion) {
        Steady steady = request.toEntity(promotion);
        ReflectionTestUtils.setField(steady, "id", 1L);
        return steady;
    }

    public static Promotion createPromotion() {
        Promotion promotion = new Promotion();
        return promotion;
    }

}
