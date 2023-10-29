package dev.steady.steady.service;

import dev.steady.global.auth.AuthFixture;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.repository.ParticipantRepository;
import dev.steady.steady.domain.repository.SteadyPositionRepository;
import dev.steady.steady.domain.repository.SteadyQuestionRepository;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.steady.domain.repository.SteadyStackRepository;
import dev.steady.steady.dto.request.SteadyPageRequest;
import dev.steady.steady.fixture.SteadyFixtures;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.fixture.UserFixtures;
import dev.steady.user.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class SteadyServiceTest {

    @Autowired
    private SteadyService steadyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SteadyRepository steadyRepository;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private SteadyStackRepository steadyStackRepository;

    @Autowired
    private SteadyQuestionRepository steadyQuestionRepository;

    @Autowired
    private SteadyPositionRepository steadyPositionRepository;

    @Test
    @DisplayName("스터디 생성 요청을 통해 스테디와 스테디 관련 정보를 생성할 수 있다.")
    void createSteadyTest() {
        var position = UserFixtures.createPosition();
        var savedPosition = positionRepository.save(position);
        var user = UserFixtures.createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = UserFixtures.createStack();
        var savedStack = stackRepository.save(stack);
        var authContext = AuthFixture.createAuthContext(savedUser.getId());

        var steadyRequest = SteadyFixtures.createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, authContext);

        var steady = steadyRepository.findById(steadyId).get();
        var participants = participantRepository.findBySteadyId(steadyId);
        var steadyStacks = steadyStackRepository.findBySteadyId(steadyId);
        var steadyQuestions = steadyQuestionRepository.findBySteadyId(steadyId);
        var steadyPositions = steadyPositionRepository.findBySteadyId(steadyId);

        assertAll(
                () -> assertThat(steady.getId()).isEqualTo(steadyId),
                () -> assertThat(participants.size()).isEqualTo(steady.getParticipants().getNumberOfParticipants()),
                () -> assertThat(steadyStacks.size()).isEqualTo(steady.getSteadyStacks().size()),
                () -> assertThat(steadyQuestions.size()).isEqualTo(steadyRequest.questions().size()),
                () -> assertThat(steadyPositions.size()).isEqualTo(steadyRequest.positions().size())
        );
    }

    @Test
    @DisplayName("스테디 페이징 조회 요청을 통해 페이징 처리 된 응답을 반환할 수 있다.")
    void getSteadiesPageTest() {
        var position = UserFixtures.createPosition();
        var savedPosition = positionRepository.save(position);
        var user = UserFixtures.createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = UserFixtures.createStack();
        var savedStack = stackRepository.save(stack);
        var authContext = AuthFixture.createAuthContext(savedUser.getId());

        var steadyRequest = SteadyFixtures.createSteadyRequest(savedStack.getId(), savedPosition.getId());
        steadyService.create(steadyRequest, authContext);

        var steadyPageRequest = new SteadyPageRequest(0, "asc");
        var steadiesResponse = steadyService.getSteadies(steadyPageRequest);

        List<Steady> steadies = steadyRepository.findAll();
        var content = steadiesResponse.content();

        assertThat(content.size()).isEqualTo(steadies.size());
    }

}
