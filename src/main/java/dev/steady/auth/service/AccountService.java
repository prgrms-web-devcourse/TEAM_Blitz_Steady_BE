package dev.steady.auth.service;

import dev.steady.auth.domain.Account;
import dev.steady.auth.domain.repository.AccountRepository;
import dev.steady.auth.dto.request.AccountCreateRequest;
import dev.steady.auth.dto.response.AccountResponse;
import dev.steady.oauth.domain.Platform;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Long create(AccountCreateRequest request) {
        Account account = AccountCreateRequest.toEntity(request.platform(), request.platformId());
        Account saved = accountRepository.save(account);

        return saved.getId();
    }

    public AccountResponse findByPlatformAndPlatformId(Platform platform, String platformId) {
        Account account = accountRepository.findByPlatformAndPlatformId(platform, platformId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("플랫폼 %s의 id %s에 해당하는 계정이 없습니다.", platform.name(), platformId)));
        return AccountResponse.from(account);
    }

}
