package dev.steady.steady.dto.response;

import dev.steady.steady.domain.Participant;
import dev.steady.user.domain.User;

public record ParticipantResponse(
        Long id,
        String nickname,
        String profileImage
) {

    public static ParticipantResponse from(Participant participant) {
        User user = participant.getUser();
        return new ParticipantResponse(user.getId(), user.getNickname(), user.getProfileImage());
    }

}
