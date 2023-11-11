package dev.steady.application.service;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.ApplicationStatus;
import dev.steady.application.domain.repository.ApplicationRepository;
import dev.steady.application.dto.request.ApplicationStatusUpdateRequest;
import dev.steady.application.dto.response.ApplicationDetailResponse;
import dev.steady.application.dto.response.ApplicationSummaryResponse;
import dev.steady.application.dto.response.CreateApplicationResponse;
import dev.steady.application.dto.response.SliceResponse;
import dev.steady.global.exception.ForbiddenException;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.steady.exception.LeaderPermissionNeededException;
import dev.steady.user.domain.Position;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static dev.steady.application.domain.ApplicationStatus.ACCEPTED;
import static dev.steady.application.domain.ApplicationStatus.REJECTED;
import static dev.steady.application.fixture.ApplicationFixture.createApplication;
import static dev.steady.application.fixture.SurveyResultFixture.createSurveyResultRequests;
import static dev.steady.global.auth.AuthFixture.createUserInfo;
import static dev.steady.steady.fixture.SteadyFixtures.creatSteady;
import static dev.steady.user.fixture.UserFixtures.createFirstUser;
import static dev.steady.user.fixture.UserFixtures.createPosition;
import static dev.steady.user.fixture.UserFixtures.createSecondUser;
import static dev.steady.user.fixture.UserFixtures.createStack;
import static dev.steady.user.fixture.UserFixtures.createThirdUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SteadyRepository steadyRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private Position position;
    private User leader;
    private Stack stack;

    @BeforeEach
    void setUp() {
        this.position = positionRepository.save(createPosition());
        this.leader = userRepository.save(createFirstUser(position));
        this.stack = stackRepository.save(createStack());
    }

    @AfterEach
    void tearDown() {
        applicationRepository.deleteAll();
        steadyRepository.deleteAll();
        userRepository.deleteAll();
        positionRepository.deleteAll();
        stackRepository.deleteAll();
    }

    @DisplayName("스테디 신청을 받아 신청서를 생성한다.")
    @Test
    void createApplicationTest() {
        //given
        var user = userRepository.save(createSecondUser(position));
        var steady = steadyRepository.save(creatSteady(user, stack));
        var surveyResultRequests = createSurveyResultRequests();
        var userInfo = createUserInfo(user.getId());

        //when
        CreateApplicationResponse response = applicationService.createApplication(steady.getId(),
                surveyResultRequests,
                userInfo);

        //then
        assertThat(response.applicationId()).isNotNull();
    }

    @DisplayName("스터디 리더는 신청서 목록 조회를 할 수 있다.")
    @Test
    void getApplicationsTest() {
        //given
        var steady = steadyRepository.save(creatSteady(leader, stack));
        var secondUser = userRepository.save(createSecondUser(position));
        var thirdUser = userRepository.save(createThirdUser(position));
        applicationRepository.saveAll(List.of(createApplication(secondUser, steady),
                createApplication(thirdUser, steady)));
        //when
        SliceResponse<ApplicationSummaryResponse> response = applicationService.getApplications(steady.getId(),
                createUserInfo(leader.getId()),
                PageRequest.of(0, 10));
        //then
        assertAll(
                () -> assertThat(response.numberOfElements()).isEqualTo(2),
                () -> assertThat(response.hasNext()).isFalse(),
                () -> assertThat(response.content()).hasSize(2)
                        .extracting(ApplicationSummaryResponse::nickname)
                        .containsExactly("Jun", "Young"));
    }

    @DisplayName("스터디 리더가 아니면 신청서 목록 조회를 할 수 없다.")
    @Test
    void getApplicationsFailTest() {
        //given
        var steady = steadyRepository.save(creatSteady(leader, stack));
        var secondUser = userRepository.save(createSecondUser(position));
        var thirdUser = userRepository.save(createThirdUser(position));
        applicationRepository.save(createApplication(secondUser, steady));
        var userInfo = createUserInfo(thirdUser.getId());
        var pageRequest = PageRequest.of(0, 10);
        //when
        //then
        assertThatThrownBy(() -> applicationService.getApplications(steady.getId(),
                userInfo,
                pageRequest)
        ).isInstanceOf(LeaderPermissionNeededException.class);
    }

    @DisplayName("스테디 리더는 신청서를 상세조회할 수 있다.")
    @Test
    void getApplicationDetailTest() {
        //given
        var steady = steadyRepository.save(creatSteady(leader, stack));
        var secondUser = userRepository.save(createSecondUser(position));
        var application = applicationRepository.save(createApplication(secondUser, steady));
        var userInfo = createUserInfo(leader.getId());
        //when
        ApplicationDetailResponse response = applicationService.getApplicationDetail(application.getId(), userInfo);
        //then
        assertThat(response.surveys()).hasSize(3)
                .extracting("question", "answer")
                .containsExactly(
                        tuple("질문1", "질문1"),
                        tuple("질문2", "질문2"),
                        tuple("질문3", "질문3")
                );
    }

    @DisplayName("스테디 리더가 아니면 신청서를 상세조회할 수 없다.")
    @Test
    void getApplicationDetailFailTest() {
        //given
        var steady = steadyRepository.save(creatSteady(leader, stack));
        var secondUser = userRepository.save(createSecondUser(position));
        var thirdUser = userRepository.save(createThirdUser(position));
        var application = applicationRepository.save(createApplication(secondUser, steady));
        var userInfo = createUserInfo(thirdUser.getId());
        //when
        //then
        assertThatThrownBy(() -> applicationService.getApplicationDetail(application.getId(), userInfo))
                .isInstanceOf(ForbiddenException.class);
    }

    @DisplayName("스테디 리더는 신청서의 상태가 WAITING 일 때 상태를 변경할 수 있다.")
    @Test
    void createSurveyResultTest() {
        //given
        var steady = steadyRepository.save(creatSteady(leader, stack));
        var secondUser = userRepository.save(createSecondUser(position));
        var application = applicationRepository.save(createApplication(secondUser, steady));
        var userInfo = createUserInfo(leader.getId());
        var request = new ApplicationStatusUpdateRequest(ACCEPTED);
        //when
        applicationService.updateApplicationStatus(application.getId(), request, userInfo);
        //then
        Application foundApplication = applicationRepository.getById(application.getId());
        ApplicationStatus status = foundApplication.getStatus();
        assertThat(status).isEqualTo(ACCEPTED);
    }

    @DisplayName("스테디 리더는 신청서의 상태가 WAITING가 아닐 때 상태를 변경할 수 없다.")
    @Test
    void createSurveyResultFailTest() {
        //given
        var steady = steadyRepository.save(creatSteady(leader, stack));
        var secondUser = userRepository.save(createSecondUser(position));
        var entity = createApplication(secondUser, steady);
        ReflectionTestUtils.setField(entity, "status", REJECTED);
        var application = applicationRepository.save(entity);
        var userInfo = createUserInfo(leader.getId());
        var request = new ApplicationStatusUpdateRequest(ACCEPTED);
        //when
        //then
        assertThatThrownBy(() -> {
            applicationService.updateApplicationStatus(application.getId(), request, userInfo);
        }).isInstanceOf(ForbiddenException.class);
    }

}
