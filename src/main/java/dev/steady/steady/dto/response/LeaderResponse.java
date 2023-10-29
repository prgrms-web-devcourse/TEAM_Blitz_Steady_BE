package dev.steady.steady.dto.response;

import dev.steady.user.domain.User;

public record LeaderResponse(
        Long id,
        String nickname,
        String profileImage
) {

    public static LeaderResponse from(User leader) {
        return new LeaderResponse(leader.getId(), leader.getNickname(), leader.getProfileImage());
    }

}
