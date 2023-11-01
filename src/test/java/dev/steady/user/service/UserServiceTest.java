package dev.steady.user.service;

import dev.steady.user.domain.Stack;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.domain.repository.UserRepository;
import dev.steady.user.domain.repository.UserStackRepository;
import dev.steady.user.dto.request.UserCreateRequest;
import dev.steady.user.fixture.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StackRepository stackRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserStackRepository userStackRepository;

    @DisplayName("프로필 정보를 입력 받아 유저를 생성한다.")
    @Test
    void createUser() {
        // given
        var savedPosition = positionRepository.save(UserFixtures.createPosition());
        var savedStacks = stackRepository.saveAll(
                IntStream.range(0, 3)
                        .mapToObj(i -> UserFixtures.createStack())
                        .toList());
        var stackIds = savedStacks.stream().map(Stack::getId).toList();

        // when
        var request = new UserCreateRequest(1L, "Nickname", savedPosition.getId(), stackIds);
        var userId = userService.createUser(request);

        var userStacks = userStackRepository.findAllByUserId(userId);

        // then
        var user = userRepository.findById(userId).get();
        assertAll(
                () -> assertThat(user.getId()).isEqualTo(userId),
                () -> assertThat(user.getPosition()).isEqualTo(savedPosition),
                () -> assertThat(userStacks).hasSameSizeAs(request.stackIds())
        );
    }

}
