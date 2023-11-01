package dev.steady.user.dto.request;

import dev.steady.user.domain.Position;
import dev.steady.user.domain.User;

import java.util.List;

public record CreateUserRequest(
        Long accountId,
        String nickname,
        Long positionId,
        List<Long> stackIds
) {
    public User toEntity(Position position) {
        return new User(this.nickname, position);
    }

}
