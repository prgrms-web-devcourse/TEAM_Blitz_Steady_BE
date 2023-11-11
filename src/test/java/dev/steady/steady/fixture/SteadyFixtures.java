package dev.steady.steady.fixture;

import dev.steady.steady.domain.ScheduledPeriod;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.domain.SteadyPosition;
import dev.steady.steady.domain.SteadyQuestion;
import dev.steady.steady.domain.SteadyStatus;
import dev.steady.steady.domain.SteadyType;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.steady.dto.request.SteadyUpdateRequest;
import dev.steady.steady.dto.response.PageResponse;
import dev.steady.steady.dto.response.ParticipantResponse;
import dev.steady.steady.dto.response.ParticipantsResponse;
import dev.steady.steady.dto.response.SteadySearchResponse;
import dev.steady.user.domain.Position;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;
import dev.steady.user.fixture.UserFixtures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static dev.steady.steady.domain.SteadyMode.ONLINE;
import static dev.steady.steady.domain.SteadyType.STUDY;

public class SteadyFixtures {

    public static SteadyCreateRequest createSteadyRequest(Long stackId, Long positionId) {
        return SteadyCreateRequest.builder()
                .name("테스트 스테디")
                .bio("무슨 스터디")
                .type(STUDY)
                .participantLimit(6)
                .steadyMode(ONLINE)
                .scheduledPeriod("ONE_WEEK")
                .deadline(LocalDate.now().plusDays(14))
                .title("스테디원 모집합니다")
                .content("많관부")
                .positions(List.of(positionId))
                .stacks(List.of(stackId))
                .questions(List.of("1번 질문", "2번 질문", "3번 질문"))
                .build();
    }

    public static SteadyUpdateRequest createSteadyUpdateRequest(Long stackId, Long positionId) {
        return SteadyUpdateRequest.builder()
                .name("업데이트 스테디")
                .bio("업데이트 적용한 스터디")
                .type(SteadyType.PROJECT)
                .status(SteadyStatus.CLOSED)
                .participantLimit(5)
                .steadyMode(SteadyMode.BOTH)
                .scheduledPeriod("TWO_WEEK")
                .deadline(LocalDate.now().plusDays(14))
                .title("스테디가 진행중입니다.")
                .content("앞으로 화이팅!")
                .positions(List.of(positionId))
                .stacks(List.of(stackId))
                .build();
    }

    public static Steady creatSteady(User user, Stack stack) {
        return Steady.builder()
                .name("스테디")
                .bio("boi")
                .type(STUDY)
                .participantLimit(6)
                .scheduledPeriod(ScheduledPeriod.FIVE_MONTH)
                .deadline(LocalDate.of(2025, 1, 2))
                .title("title")
                .content("content")
                .user(user)
                .stacks(List.of(stack))
                .steadyMode(ONLINE)
                .build();
    }

    public static SteadyPosition createSteadyPosition(Steady steady, Position position) {
        return SteadyPosition.builder().steady(steady).position(position).build();
    }

    public static List<SteadyQuestion> createSteadyQuestion(Steady steady, List<String> questions) {
        return IntStream.range(0, questions.size())
                .mapToObj(index -> dev.steady.steady.domain.SteadyQuestion.builder()
                        .content(questions.get(index))
                        .sequence(index + 1)
                        .steady(steady)
                        .build())
                .toList();
    }

    public static Steady createSteady() {
        var user = UserFixtures.createFirstUser(UserFixtures.createPosition());
        var stack = UserFixtures.createStack();
        var steady = createSteadyRequest(1L, 1L).toEntity(user, List.of(stack));
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(stack, "id", 1L);
        ReflectionTestUtils.setField(steady, "id", 1L);
        ReflectionTestUtils.setField(steady, "createdAt", LocalDateTime.of(2023, 12, 7, 11, 11));
        return steady;
    }

    public static PageResponse<SteadySearchResponse> createSteadyPageResponse(Steady steady, Pageable pageable) {
        Page<Steady> steadies = new PageImpl<>(List.of(steady), pageable, 1);
        return PageResponse.from(steadies.map(SteadySearchResponse::from));
    }

    public static ParticipantsResponse createParticipantsResponse() {
        return new ParticipantsResponse(List.of(
                new ParticipantResponse(1L, "weonest", "url1", true),
                new ParticipantResponse(2L, "nayjk", "url2", false)
        ));
    }

}
