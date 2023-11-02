package dev.steady.steady.service;

import dev.steady.steady.domain.Participant;
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
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.steady.global.auth.AuthFixture.createUserInfo;
import static dev.steady.steady.fixture.SteadyFixtures.createSteadyRequest;
import static dev.steady.steady.fixture.SteadyFixtures.createSteadyUpdateRequest;
import static dev.steady.user.fixture.UserFixtures.createAnotherPosition;
import static dev.steady.user.fixture.UserFixtures.createAnotherStack;
import static dev.steady.user.fixture.UserFixtures.createAnotherUser;
import static dev.steady.user.fixture.UserFixtures.createPosition;
import static dev.steady.user.fixture.UserFixtures.createStack;
import static dev.steady.user.fixture.UserFixtures.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class SteadyServiceTest {

    @Autowired
    private EntityManager entityManager;

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
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        // when
        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);
        entityManager.flush();
        entityManager.clear();

        // then
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
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        steadyService.create(steadyRequest, userInfo);
        entityManager.flush();
        entityManager.clear();

        // when
        var steadyPageRequest = new SteadyPageRequest(0, "asc");
        var steadiesResponse = steadyService.getSteadies(steadyPageRequest);

        // then
        List<Steady> steadies = steadyRepository.findAll();
        var content = steadiesResponse.content();
        assertThat(content.size()).isEqualTo(steadies.size());
    }

    @Test
    @DisplayName("스테디 식별자를 통해 스테디 상세 조회를 할 수 있다.")
    void getDetailSteadyTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);
        entityManager.flush();
        entityManager.clear();

        // when
        var response = steadyService.getDetailSteady(steadyId, userInfo);

        // then
        var steady = steadyRepository.findById(steadyId).get();
        var positions = steadyPositionRepository.findBySteadyId(steadyId);
        assertAll(
                () -> assertThat(response.id()).isEqualTo(steady.getId()),
                () -> assertThat(response.leaderResponse()).isEqualTo(LeaderResponse.from(steady.getParticipants().getLeader())),
                () -> assertThat(response.name()).isEqualTo(steady.getName()),
                () -> assertThat(response.title()).isEqualTo(steady.getTitle()),
                () -> assertThat(response.type()).isEqualTo(steady.getType()),
                () -> assertThat(response.status()).isEqualTo(steady.getStatus()),
                () -> assertThat(response.participantLimit()).isEqualTo(String.valueOf(steady.getParticipantLimit())),
                () -> assertThat(response.numberOfParticipants()).isEqualTo(String.valueOf(steady.getNumberOfParticipants())),
                () -> assertThat(response.steadyMode()).isEqualTo(steady.getSteadyMode()),
                () -> assertThat(response.scheduledPeriod()).isEqualTo(steady.getScheduledPeriod()),
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
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var anotherPosition = createAnotherPosition();
        var savedAnotherPosition = positionRepository.save(anotherPosition);
        var anotherStack = createAnotherStack();
        var savedAnotherStack = stackRepository.save(anotherStack);
        entityManager.flush();
        entityManager.clear();

        // when
        var steadyUpdateRequest = createSteadyUpdateRequest(savedAnotherStack.getId(), savedAnotherPosition.getId());
        var updatedSteadyId = steadyService.updateSteady(steadyId, userInfo, steadyUpdateRequest);
        entityManager.flush();
        entityManager.clear();

        // then
        var updatedSteady = steadyRepository.findById(updatedSteadyId).get();
        List<SteadyStack> steadyStacks = steadyStackRepository.findBySteadyId(steadyId);
        assertAll(
                () -> assertThat(updatedSteady.getName()).isEqualTo(steadyUpdateRequest.name()),
                () -> assertThat(updatedSteady.getBio()).isEqualTo(steadyUpdateRequest.bio()),
                () -> assertThat(updatedSteady.getType()).isEqualTo(steadyUpdateRequest.type()),
                () -> assertThat(updatedSteady.getStatus()).isEqualTo(steadyUpdateRequest.status()),
                () -> assertThat(updatedSteady.getParticipantLimit()).isEqualTo(steadyUpdateRequest.participantLimit()),
                () -> assertThat(updatedSteady.getSteadyMode()).isEqualTo(steadyUpdateRequest.steadyMode()),
                () -> assertThat(String.valueOf(updatedSteady.getScheduledPeriod())).isEqualTo(steadyUpdateRequest.scheduledPeriod()),
                () -> assertThat(updatedSteady.getDeadline()).isEqualTo(steadyUpdateRequest.deadline()),
                () -> assertThat(updatedSteady.getTitle()).isEqualTo(steadyUpdateRequest.title()),
                () -> assertThat(updatedSteady.getContent()).isEqualTo(steadyUpdateRequest.content()),
                () -> assertThat(updatedSteady.getSteadyStacks()).hasSameSizeAs(steadyStacks)
                        .extracting("id").containsExactly(steadyStacks.get(0).getId())
        );
    }

    @Test
    @DisplayName("리더가 아닌 유저가 수정 요청을 보내면 에러를 반환한다.")
    void updateSteadyByAnotherUserTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var anotherPosition = createAnotherPosition();
        var savedAnotherPosition = positionRepository.save(anotherPosition);
        var anotherStack = createAnotherStack();
        var savedAnotherStack = stackRepository.save(anotherStack);
        entityManager.flush();
        entityManager.clear();

        // when & then
        var anotherUser = createAnotherUser(savedAnotherPosition);
        var anotherUserInfo = createUserInfo(anotherUser.getId());
        var steadyUpdateRequest = createSteadyUpdateRequest(savedAnotherStack.getId(), savedAnotherPosition.getId());
        assertThatThrownBy(() -> steadyService.updateSteady(steadyId, anotherUserInfo, steadyUpdateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("스테디 리더가 끌어올리기 요청을 통해 스테디를 끌어올릴 수 있다.")
    void promoteSteadyTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);
        entityManager.flush();
        entityManager.clear();

        // when
        var steady = steadyRepository.findById(steadyId).get();
        steadyService.promoteSteady(steadyId, userInfo);
        entityManager.flush();
        entityManager.clear();

        // then
        var updatedSteady = steadyRepository.findById(steadyId).get();
        var createdAt = steady.getCreatedAt();
        var promotedAt = updatedSteady.getPromotion().getPromotedAt();
        assertThat(createdAt.isBefore(promotedAt)).isTrue();
    }

    @Test
    @DisplayName("리더가 아닌 유저가 끌어올리기 요청을 보내면 에러를 반환한다.")
    void promoteSteadyByAnotherUserTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);
        entityManager.flush();
        entityManager.clear();

        // when & then
        var anotherUser = createAnotherUser(savedPosition);
        userRepository.save(anotherUser);
        var anotherUserInfo = createUserInfo(anotherUser.getId());
        assertThatThrownBy(() -> steadyService.promoteSteady(steadyId, anotherUserInfo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("스테디 리더가 스테디를 종료 상태로 변경할 수 있다.")
    void finishSteadyTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);
        entityManager.flush();
        entityManager.clear();

        // when
        steadyService.finishSteady(steadyId, userInfo);
        entityManager.flush();
        entityManager.clear();

        // then
        var steady = steadyRepository.findById(steadyId).get();
        var status = steady.getStatus();
        assertThat(status).isEqualTo(SteadyStatus.FINISHED);
    }

    @Test
    @DisplayName("리더가 아닌 유저가 스테디 종료를 요청하면 에러를 반환한다.")
    void finishSteadyByAnotherUserTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var anotherUser = createAnotherUser(savedPosition);
        userRepository.save(anotherUser);
        var anotherUserInfo = createUserInfo(anotherUser.getId());
        entityManager.flush();
        entityManager.clear();

        // when & then
        assertThatThrownBy(() -> steadyService.finishSteady(steadyId, anotherUserInfo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("스테디 참여자가 리더뿐이며 리더가 삭제 요청을 보내면 스테디를 삭제할 수 있다.")
    void deleteSteadyTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);
        entityManager.flush();
        entityManager.clear();

        // when
        steadyService.deleteSteady(steadyId, userInfo);
        entityManager.flush();
        entityManager.clear();

        // then
        int expectedSize = 0;
        var participants = participantRepository.findBySteadyId(steadyId);
        var steadyStacks = steadyStackRepository.findBySteadyId(steadyId);
        var steadyPositions = steadyPositionRepository.findBySteadyId(steadyId);
        var steadyQuestions = steadyQuestionRepository.findBySteadyId(steadyId);
        assertAll(
                () -> assertThatThrownBy(() -> steadyRepository.getSteady(steadyId))
                        .isInstanceOf(InvalidDataAccessApiUsageException.class),
                () -> assertThat(participants).hasSize(expectedSize),
                () -> assertThat(steadyStacks).hasSize(expectedSize),
                () -> assertThat(steadyPositions).hasSize(expectedSize),
                () -> assertThat(steadyQuestions).hasSize(expectedSize)
        );
    }

    @Test
    @DisplayName("리더를 제외한 참여자가 존재하는 경우 스테디를 삭제할 수 없다.")
    void deleteSteadyWhenParticipantIsExistTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var steady = steadyRepository.findById(steadyId).get();
        var anotherUser = createAnotherUser(savedPosition);
        userRepository.save(anotherUser);

        // when
        steady.addParticipant(Participant.createMember(anotherUser, steady));
        entityManager.flush();
        entityManager.clear();

        // then
        assertThatThrownBy(() -> steadyService.deleteSteady(steadyId, userInfo))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("리더가 아닌 유저가 스테디 삭제 요청을 보내면 에러를 반환한다.")
    void deleteSteadyByAnotherUserTest() {
        // given
        var position = createPosition();
        var savedPosition = positionRepository.save(position);
        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = createStack();
        var savedStack = stackRepository.save(stack);
        var userInfo = createUserInfo(savedUser.getId());

        var steadyRequest = createSteadyRequest(savedStack.getId(), savedPosition.getId());
        var steadyId = steadyService.create(steadyRequest, userInfo);

        var anotherUser = createAnotherUser(savedPosition);
        userRepository.save(anotherUser);
        var anotherUserInfo = createUserInfo(anotherUser.getId());
        entityManager.flush();
        entityManager.clear();

        // when & then
        assertThatThrownBy(() -> steadyService.deleteSteady(steadyId, anotherUserInfo))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
