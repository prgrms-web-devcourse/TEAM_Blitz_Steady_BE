package dev.steady.auth.domain.repository;

import dev.steady.auth.domain.Account;
import dev.steady.auth.domain.Platform;
import dev.steady.global.exception.NotFoundException;
import dev.steady.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import static dev.steady.auth.exception.AuthErrorCode.ACCOUNT_NOT_FOUND;

public interface AccountRepository extends JpaRepository<Account, Long> {

    default Account getById(Long accountId) {
        return findById(accountId)
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND));
    }

    boolean existsByPlatformAndPlatformId(Platform platform, String platformId);

    Account getByPlatformAndPlatformId(Platform platform, String platformId);

    Account findByUser(User user);

    void deleteByUser(User user);

}
