package dev.steady.steady.dto.response;

import dev.steady.user.domain.User;

public record LeaderResponse(
        Long id,
        String nickname,
        String profileImage
) {

    public static LeaderResponse from(User leader) {
        // TODO: 2023-11-09  리더가 탈퇴하면 스테디를 어떻게 할 것인지
        return new LeaderResponse(leader.getId(), leader.getNickname(), leader.getProfileImage());
    }

}
