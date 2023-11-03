package dev.steady.application.service;

import dev.steady.application.domain.repository.ApplicationRepository;
import dev.steady.application.dto.request.SurveyResultRequest;
import dev.steady.application.dto.response.ApplicationSummaryResponse;
import dev.steady.application.dto.response.CreateApplicationResponse;
import dev.steady.global.auth.UserInfo;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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
        var position = positionRepository.save(createPosition());
        var user = userRepository.save(createFirstUser(position));
        var savedStack = stackRepository.save(createStack());
        var steady = steadyRepository.save(creatSteady(user, savedStack));
        List<SurveyResultRequest> surveyResultRequests = createSurveyResultRequests();
        UserInfo userInfo = createUserInfo(user.getId());

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
        var position = positionRepository.save(createPosition());
        var leader = userRepository.save(createFirstUser(position));
        var savedStack = stackRepository.save(createStack());
        var steady = steadyRepository.save(creatSteady(leader, savedStack));
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

    @DisplayName("스터디 리더가 아니팅 신청서 목록 조회를 할 수 없다.")
    @Test
    void getApplicationsFailTest() {
        //given
        var position = positionRepository.save(createPosition());
        var leader = userRepository.save(createFirstUser(position));
        var savedStack = stackRepository.save(createStack());
        var steady = steadyRepository.save(creatSteady(leader, savedStack));
        var secondUser = userRepository.save(createSecondUser(position));
        var thirdUser = userRepository.save(createThirdUser(position));
        applicationRepository.save(createApplication(secondUser, steady));
        UserInfo userInfo = createUserInfo(thirdUser.getId());
        PageRequest page = PageRequest.of(0, 10);
        //when
        //then
        assertThatThrownBy(() -> applicationService.getApplications(steady.getId(),
                userInfo,
                page)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("스테디 리더는 신청서를 상세조회 할 수 있다.")
    @Test
    void getApplicationDetailTest() {
        //given
        var position = positionRepository.save(createPosition());
        var leader = userRepository.save(createFirstUser(position));
        var savedStack = stackRepository.save(createStack());
        var steady = steadyRepository.save(creatSteady(leader, savedStack));
        var secondUser = userRepository.save(createSecondUser(position));
        var application = applicationRepository.save(createApplication(secondUser, steady));
        UserInfo userInfo = createUserInfo(leader.getId());
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

    @DisplayName("스테디 리더가 아니면 신청서를 상세조회 할 수 없다.")
    @Test
    void getApplicationDetailFailTest() {
        //given
        var position = positionRepository.save(createPosition());
        var leader = userRepository.save(createFirstUser(position));
        var savedStack = stackRepository.save(createStack());
        var steady = steadyRepository.save(creatSteady(leader, savedStack));
        var secondUser = userRepository.save(createSecondUser(position));
        var application = applicationRepository.save(createApplication(secondUser, steady));
        UserInfo userInfo = createUserInfo(secondUser.getId());
        //when
        //then
        assertThatThrownBy(() -> applicationService.getApplicationDetail(application.getId(), userInfo))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
