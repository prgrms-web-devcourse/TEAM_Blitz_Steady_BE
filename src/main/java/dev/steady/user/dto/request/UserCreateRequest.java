package dev.steady.user.dto.request;

import dev.steady.user.domain.Position;
import dev.steady.user.domain.User;

import java.util.List;

public record UserCreateRequest(
        Long accountId,
        String nickname,
        Long positionId,
        List<Long> stacksId
) {

    public User toEntity(Position position) {
        return new User(this.nickname, position);
    }

}
