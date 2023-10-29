package dev.steady.user.dto.request;

import dev.steady.user.domain.Position;
import dev.steady.user.domain.User;

public record UserCreateRequest(
        String nickname,
        Long positionId
) {

    public User toEntity(Position position) {
        // TODO: 2023-10-29 스택 정보 저장 로직 추가
        return User.builder()
                .nickname(this.nickname)
                .profileImage("기본 이미지")
                .position(position)
                .build();
    }

}
