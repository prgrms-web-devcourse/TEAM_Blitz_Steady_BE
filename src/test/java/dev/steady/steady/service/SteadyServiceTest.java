package dev.steady.steady.service;

import dev.steady.global.auth.AuthFixture;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyStack;
import dev.steady.steady.domain.repository.ParticipantRepository;
import dev.steady.steady.domain.repository.SteadyPositionRepository;
import dev.steady.steady.domain.repository.SteadyQuestionRepository;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.steady.domain.repository.SteadyStackRepository;
import dev.steady.steady.dto.request.SteadyPageRequest;
import dev.steady.steady.dto.response.LeaderResponse;
import dev.steady.steady.dto.response.SteadyStackResponse;
import dev.steady.steady.fixture.SteadyFixtures;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.fixture.UserFixtures;
import dev.steady.user.domain.repository.UserRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        var userInfo = AuthFixture.createUserInfo(savedUser.getId());

        var steadyRequest = SteadyFixtures.createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var steady = steadyRepository.findById(steadyId).get();
        var participants = participantRepository.findBySteadyId(steadyId);
        var steadyStacks = steadyStackRepository.findBySteadyId(steadyId);
        var steadyQuestions = steadyQuestionRepository.findBySteadyId(steadyId);
        var steadyPositions = steadyPositionRepository.findBySteadyId(steadyId);

        assertAll(
                () -> assertThat(steady.getId()).isEqualTo(steadyId),
                () -> assertThat(participants).hasSameSizeAs(steady.getParticipants().getAllMembers()),
                () -> assertThat(steadyStacks).hasSameSizeAs(steady.getSteadyStacks()),
                () -> assertThat(steadyQuestions).hasSameSizeAs(steadyRequest.questions()),
                () -> assertThat(steadyPositions).hasSameSizeAs(steadyRequest.positions())
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
        var userInfo = AuthFixture.createUserInfo(savedUser.getId());

        var steadyRequest = SteadyFixtures.createSteadyRequest(savedStack.getId(), savedPosition.getId());
        steadyService.create(steadyRequest, userInfo);

        var steadyPageRequest = new SteadyPageRequest(0, "asc");
        var steadiesResponse = steadyService.getSteadies(steadyPageRequest);

        List<Steady> steadies = steadyRepository.findAll();
        var content = steadiesResponse.content();

        assertThat(content.size()).isEqualTo(steadies.size());
    }

    @Test
    @DisplayName("스테디 식별자를 통해 스테디 상세 조회를 할 수 있다.")
    void getSteadyTest() {
        var position = UserFixtures.createPosition();
        var savedPosition = positionRepository.save(position);
        var user = UserFixtures.createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = UserFixtures.createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = AuthFixture.createUserInfo(savedUser.getId());

        var steadyRequest = SteadyFixtures.createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var steady = steadyRepository.findById(steadyId).get();
        var positions = steadyPositionRepository.findBySteadyId(steadyId);

        var response = steadyService.getDetailSteady(steadyId, userInfo);

        assertAll(
                () -> assertThat(response.id()).isEqualTo(steady.getId()),
                () -> assertThat(response.leaderResponse()).isEqualTo(LeaderResponse.from(steady.getParticipants().getLeader())),
                () -> assertThat(response.name()).isEqualTo(steady.getName()),
                () -> assertThat(response.title()).isEqualTo(steady.getTitle()),
                () -> assertThat(response.type()).isEqualTo(steady.getType()),
                () -> assertThat(response.status()).isEqualTo(steady.getStatus()),
                () -> assertThat(response.participantLimit()).isEqualTo(steady.getParticipantLimit()),
                () -> assertThat(response.numberOfParticipants()).isEqualTo(steady.getNumberOfParticipants()),
                () -> assertThat(response.steadyMode()).isEqualTo(steady.getSteadyMode()),
                () -> assertThat(response.openingDate()).isEqualTo(steady.getOpeningDate()),
                () -> assertThat(response.deadline()).isEqualTo(steady.getDeadline()),
                () -> assertThat(response.title()).isEqualTo(steady.getTitle()),
                () -> assertThat(response.content()).isEqualTo(steady.getContent()),
                () -> assertThat(response.positions()).isEqualTo(positions.stream()
                        .map(v -> v.getPosition().getName())
                        .toList()),
                () -> assertThat(response.stacks()).isEqualTo(steady.getSteadyStacks().stream()
                        .map(SteadyStackResponse::from)
                        .toList()),
                () -> assertThat(response.isLeader()).isTrue(),
                () -> assertThat(response.isSubmittedUser()).isFalse()
        );
    }

    @Test
    @DisplayName("스테디 수정 요청을 통해 스테디 정보를 수정할 수 있다.")
    void steadyUpdateTest() {
        var position = UserFixtures.createPosition();
        var savedPosition = positionRepository.save(position);
        var user = UserFixtures.createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = UserFixtures.createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = AuthFixture.createUserInfo(savedUser.getId());

        var steadyRequest = SteadyFixtures.createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var anotherPosition = UserFixtures.createAnotherPosition();
        var savedAnotherPosition = positionRepository.save(anotherPosition);
        var anotherStack = UserFixtures.createAnotherStack();
        var savedAnotherStack = stackRepository.save(anotherStack);

        var steadyUpdateRequest = SteadyFixtures.createSteadyUpdateRequest(savedAnotherPosition.getId(), savedAnotherStack.getId());
        var updatedSteadyId = steadyService.updateSteady(steadyId, userInfo, steadyUpdateRequest);

        var updatedSteady = steadyRepository.findById(updatedSteadyId).get();
        List<SteadyStack> steadyStacks = steadyStackRepository.findBySteadyId(steadyId);

        assertAll(
                () -> assertThat(updatedSteady.getName()).isEqualTo(steadyUpdateRequest.name()),
                () -> assertThat(updatedSteady.getBio()).isEqualTo(steadyUpdateRequest.bio()),
                () -> assertThat(updatedSteady.getType()).isEqualTo(steadyUpdateRequest.type()),
                () -> assertThat(updatedSteady.getStatus()).isEqualTo(steadyUpdateRequest.status()),
                () -> assertThat(updatedSteady.getParticipantLimit()).isEqualTo(steadyUpdateRequest.participantLimit()),
                () -> assertThat(updatedSteady.getSteadyMode()).isEqualTo(steadyUpdateRequest.steadyMode()),
                () -> assertThat(updatedSteady.getOpeningDate()).isEqualTo(steadyUpdateRequest.openingDate()),
                () -> assertThat(updatedSteady.getDeadline()).isEqualTo(steadyUpdateRequest.deadline()),
                () -> assertThat(updatedSteady.getTitle()).isEqualTo(steadyUpdateRequest.title()),
                () -> assertThat(updatedSteady.getContent()).isEqualTo(steadyUpdateRequest.content()),
                () -> assertThat(updatedSteady.getSteadyStacks()).hasSameSizeAs(steadyStacks)
                        .extracting("id").containsExactly(steadyStacks.get(0).getId())
        );
    }

    @Test
    @DisplayName("스테디 리더가 끌어올리기 요청을 통해 스테디를 끌어올릴 수 있다.")
    void promoteSteadyTest() {
        var position = UserFixtures.createPosition();
        var savedPosition = positionRepository.save(position);
        var user = UserFixtures.createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = UserFixtures.createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = AuthFixture.createUserInfo(savedUser.getId());

        var steadyRequest = SteadyFixtures.createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var steady = steadyRepository.findById(steadyId).get();
        var createdAt = steady.getCreatedAt();

        steadyService.promoteSteady(steadyId, userInfo);

        var updatedSteady = steadyRepository.findById(steadyId).get();
        var promotedAt = updatedSteady.getPromotion().getPromotedAt();

        assertThat(createdAt.isBefore(promotedAt)).isTrue();
    }

}
