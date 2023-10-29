package dev.steady.auth.domain.repository;

import dev.steady.auth.domain.Account;
import dev.steady.oauth.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByPlatformAndPlatformId(Platform platform, String platformId);

    Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId);

}
