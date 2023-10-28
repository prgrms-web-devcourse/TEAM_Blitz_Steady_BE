package dev.steady.auth.service;

import dev.steady.auth.domain.Account;
import dev.steady.auth.domain.repository.AccountRepository;
import dev.steady.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void registUser(Long accountId, User user) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("일치하는 계정이 없습니다: %d", accountId)));
        account.registUser(user);
    }

}
