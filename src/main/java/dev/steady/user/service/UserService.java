package dev.steady.user.service;

import dev.steady.auth.service.AccountService;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.UserRepository;
import dev.steady.user.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;

    @Transactional
    public Long createUser(Long accountId, UserCreateRequest request) {
        User user = UserCreateRequest.toEntity(request);
        User saved = userRepository.save(user);
        accountService.registUser(accountId, saved);
        return saved.getId();
    }

}
