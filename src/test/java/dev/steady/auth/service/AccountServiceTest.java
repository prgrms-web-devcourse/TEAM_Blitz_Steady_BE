package dev.steady.auth.service;

import dev.steady.auth.domain.repository.AccountRepository;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.UserRepository;
import dev.steady.user.fixture.UserFixtures;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static dev.steady.auth.service.Fixtures.AccountFixture.createAccount;
import static dev.steady.user.fixture.UserFixtures.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @Test
    @DisplayName("Account가 존재한다면 User를 등록할 수 있다.")
    void registerUser() {
        // given
        var account = createAccount();
        var savedAccount = accountRepository.save(account);

        var position = UserFixtures.createPosition();
        var savedPosition = positionRepository.save(position);

        var user = createUser(savedPosition);
        var savedUser = userRepository.save(user);

        // when
        accountService.registerUser(savedAccount.getId(), savedUser.getId());

        // then
        assertAll(
                () -> assertThat(savedAccount).isNotNull(),
                () -> assertThat(savedAccount.getId()).isEqualTo(savedUser.getId())
        );
    }

}
