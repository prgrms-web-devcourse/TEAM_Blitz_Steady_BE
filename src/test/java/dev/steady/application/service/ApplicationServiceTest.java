package dev.steady.application.service;

import dev.steady.application.domain.repository.ApplicationRepository;
import dev.steady.application.dto.request.SurveyResultRequest;
import dev.steady.application.dto.response.CreateApplicationResponse;
import dev.steady.global.auth.AuthFixture;
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

import java.util.List;

import static dev.steady.application.fixture.SurveyResultFixture.createSurveyResultRequests;
import static dev.steady.steady.fixture.SteadyFixtures.creatSteady;
import static dev.steady.user.fixture.UserFixtures.createPosition;
import static dev.steady.user.fixture.UserFixtures.createStack;
import static dev.steady.user.fixture.UserFixtures.createUser;
import static org.assertj.core.api.Assertions.assertThat;

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

    }

    @DisplayName("스테디 신청을 받아 신청서를 생성한다.")
    @Test
    void createApplicationTest() {
        //given
        var position = positionRepository.save(createPosition());
        var user = userRepository.save(createUser(position));
        var savedStack = stackRepository.save(createStack());
        var steady = steadyRepository.save(creatSteady(user, savedStack));
        List<SurveyResultRequest> surveyResultRequests = createSurveyResultRequests();
        UserInfo userInfo = AuthFixture.createUserInfo(user.getId());

        //when
        CreateApplicationResponse response = applicationService.createApplication(steady.getId(),
                surveyResultRequests,
                userInfo);

        //then
        assertThat(response.applicationId()).isNotNull();
    }

}
