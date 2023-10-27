package dev.steady.user.dto.request;

import dev.steady.user.domain.User;

public record UserCreateRequest(
        String nickname
) {

    public static User toEntity(UserCreateRequest request) {
        // TODO: 2023-10-26 스택, 포지션 정보 저장 로직 추가
        return User.builder()
                .nickname(request.nickname())
                .profileImage("기본 이미지")
                .build();
    }

}
