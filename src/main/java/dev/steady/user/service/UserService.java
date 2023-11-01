package dev.steady.user.service;

import dev.steady.user.domain.Position;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;
import dev.steady.user.domain.UserStack;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.domain.repository.UserRepository;
import dev.steady.user.domain.repository.UserStackRepository;
import dev.steady.user.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StackRepository stackRepository;
    private final PositionRepository positionRepository;
    private final UserStackRepository userStackRepository;

    @Transactional
    public Long createUser(UserCreateRequest request) {
        Position position = getPosition(request.positionId());

        User user = request.toEntity(position);
        User savedUser = userRepository.save(user);

        List<UserStack> userStacks = createUserStacks(request.stackIds(), savedUser);
        userStackRepository.saveAll(userStacks);

        return savedUser.getId();
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    private List<Stack> getStacks(List<Long> stackIds) {
        return stackIds.stream()
                .map(stackId -> stackRepository.findById(stackId).orElseThrow(IllegalArgumentException::new))
                .toList();
    }

    private Position getPosition(Long positionId) {
        return positionRepository.findById(positionId).orElseThrow(IllegalArgumentException::new);
    }

    private List<UserStack> createUserStacks(List<Long> stackIds, User user) {
        List<Stack> stacks = getStacks(stackIds);
        return stacks.stream().map(stack -> new UserStack(user, stack)).toList();
    }

}
