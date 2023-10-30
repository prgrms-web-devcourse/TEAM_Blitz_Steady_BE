package dev.steady.auth.domain.repository;

import dev.steady.auth.domain.Account;
import dev.steady.auth.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByPlatformAndPlatformId(Platform platform, String platformId);

    Account findByPlatformAndPlatformId(Platform platform, String platformId);

}
