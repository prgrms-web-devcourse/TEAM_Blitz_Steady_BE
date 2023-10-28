package dev.steady.steady.service;

import dev.steady.global.auth.AuthContext;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.repository.SteadyRepository;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.steady.dto.request.SteadyPageRequest;
import dev.steady.steady.dto.response.PageResponse;
import dev.steady.steady.dto.response.SteadySearchResponse;
import dev.steady.steady.fixture.SteadyFixtures;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.fixture.UserFixtures;
import dev.steady.user.infrastructure.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class SteadyServiceTest {

    @Autowired
    private SteadyService steadyService;

    @Autowired
    private AuthContext authContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SteadyRepository steadyRepository;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private PositionRepository positionRepository;

    @BeforeEach
    void setUp() {
        var position = UserFixtures.createPosition();
        var savedPosition = positionRepository.save(position);
        var user = UserFixtures.createUser(savedPosition);
        var savedUser = userRepository.save(user);
        var stack = UserFixtures.createStack();
        stackRepository.save(stack);
        authContext.setUserId(savedUser.getId());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("스터디 생성 요청을 통해 스테디 관련 정보와 스테디를 생성할 수 있다.")
    void createSteadyTest() {
        SteadyCreateRequest steadyRequest = SteadyFixtures.createSteadyRequest();
        var returnedId = steadyService.create(steadyRequest, authContext);
        Steady steady = steadyRepository.findById(returnedId).get();

        assertAll(
                () -> assertThat(steady).isNotNull(),
                () -> assertThat(steady.getId()).isEqualTo(returnedId)
        );
    }

    @Test
    @DisplayName("스테디 페이징 조회 요청을 통해 페이징 처리 된 응답을 반환할 수 있다.")
    void getSteadiesPageTest() {
        SteadyPageRequest asc = new SteadyPageRequest(0, "asc");
        PageResponse<SteadySearchResponse> steadies = steadyService.getSteadies(asc);

    }
}
