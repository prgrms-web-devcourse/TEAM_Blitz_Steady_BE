package dev.steady.review.dto.response;

import dev.steady.steady.domain.Participant;
import dev.steady.user.domain.User;

public record RevieweeResponse(
        Long userId,
        String nickname,
        String profileImage
) {

    public static RevieweeResponse from(Participant participant) {
        User user = participant.getUser();
        return new RevieweeResponse(
                user.getId(),
                user.getNickname(),
                user.getProfileImage()
        );
    }

}
