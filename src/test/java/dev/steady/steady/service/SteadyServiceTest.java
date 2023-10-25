package dev.steady.steady.service;

import dev.steady.global.auth.AuthContext;
import dev.steady.steady.domain.Promotion;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyQuestion;
import dev.steady.steady.domain.repository.SteadyQuestionRepository;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.steady.fixture.SteadyFixtures;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.user.domain.User;
import dev.steady.user.fixture.UserFixtures;
import dev.steady.user.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SteadyServiceTest {

    @InjectMocks
    private SteadyService steadyService;

    @Mock
    private SteadyRepository steadyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SteadyQuestionRepository steadyQuestionRepository;

    @Mock
    private AuthContext authContext;

    @Test
    @DisplayName("스터디 생성 요청을 통해 스터디와 스테디 질문을 생성할 수 있다")
    void createSteadyTest() {
        // given
        User user = UserFixtures.createUser();
        SteadyCreateRequest steadyRequest = SteadyFixtures.createSteadyRequest();
        Promotion promotion = SteadyFixtures.createPromotion();
        Steady steady = SteadyFixtures.createSteady(steadyRequest, promotion);
        List<SteadyQuestion> steadyQuestions = SteadyFixtures.createSteadyQuestions(steadyRequest.questionList(), steady);

        given(authContext.getUserId()).willReturn(user.getId());
        given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));
        given(steadyRepository.save(any())).willReturn(steady);

        // when
        Long returnedId = steadyService.create(steadyRequest);

        // then
        assertThat(returnedId).isEqualTo(steady.getId());
        then(steadyQuestionRepository).should(atMostOnce()).saveAll(any());
        // steadyQuestionRepository의 동작을 given에서 처리하면 불필요한 stubbing 동작이라는 예외
    }

}
