package dev.steady.application.dto.response;

import dev.steady.application.domain.Application;

public record ApplicationSummaryResponse(
        Long id,
        String nickname,
        String profileImage
) {
    public static ApplicationSummaryResponse from(Application applications) {
        return new ApplicationSummaryResponse(applications.getId(),
                applications.getUser().getNickname(),
                applications.getUser().getProfileImage());
    }

}
