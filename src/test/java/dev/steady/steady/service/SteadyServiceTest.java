package dev.steady.steady.service;

import dev.steady.global.auth.AuthContext;
import dev.steady.steady.dto.request.SteadyCreateRequest;
import dev.steady.steady.fixture.SteadyFixtures;
import dev.steady.user.domain.Position;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.fixture.UserFixtures;
import dev.steady.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class SteadyServiceTest {

    @Autowired
    private SteadyService steadyService;

    @Autowired
    private AuthContext authContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StackRepository stackRepository;

    @Autowired
    private PositionRepository positionRepository;

    @BeforeEach
    void setUp() {

        Position position = UserFixtures.createPosition();
        Position savedPosition = positionRepository.save(position);

        User user = UserFixtures.createUser(savedPosition);
        User savedUser = userRepository.save(user);

        Stack stack = UserFixtures.createStack();
        Stack savedStack = stackRepository.save(stack);

        authContext.setUserId(savedUser.getId());

    }

    @Test
    @DisplayName("스터디 생성 요청을 통해 스테디 관련 정보와 스테디를 생성할 수 있다.")
    void createSteadyTest() {

        SteadyCreateRequest steadyRequest = SteadyFixtures.createSteadyRequest();
        Long returnedId = steadyService.create(steadyRequest, authContext);



        // given
    }

}
