package dev.steady.application.dto.response;

import dev.steady.application.domain.Application;
import dev.steady.user.domain.User;

public record ApplicationSummaryResponse(
        Long applicationId,
        Long userId,
        String nickname,
        String profileImage
) {

    public static ApplicationSummaryResponse from(Application applications) {
        User writer = applications.getUser();
        return new ApplicationSummaryResponse(
                applications.getId(),
                writer.getId(),
                writer.getNickname(),
                writer.getProfileImage());
    }

}
