package dev.steady.user.service;

import dev.steady.auth.service.AccountService;
import dev.steady.user.domain.Position;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.UserRepository;
import dev.steady.user.dto.request.UserCreateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final AccountService accountService;

    @Transactional
    public Long createUser(Long accountId, UserCreateRequest request) {
        Position position = positionRepository.findById(request.positionId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("해당하는 포지션이 없습니다:%d", request.positionId())));

        User user = request.toEntity(position);
        User saved = userRepository.save(user);
        accountService.registUser(accountId, saved);
        return saved.getId();
    }

}
