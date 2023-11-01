package dev.steady.auth.service;

import dev.steady.auth.domain.repository.AccountRepository;
import dev.steady.auth.service.Fixtures.AccountFixture;
import dev.steady.user.domain.repository.UserRepository;
import dev.steady.user.fixture.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Account에 user를 등록할 수 있다.")
    void registerUser() {
        // given
        var account = AccountFixture.createAccount();
        var user = UserFixtures.createUser(UserFixtures.createPosition());

        var savedAccount = accountRepository.save(account);
        var savedUser = userRepository.save(user);
        
        // when
        accountService.registerUser(savedAccount.getId(), savedUser.getId());

        // then
        assertThat(account.getUser().getId()).isEqualTo(savedUser.getId());
    }
}
