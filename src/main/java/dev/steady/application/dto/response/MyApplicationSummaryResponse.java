package dev.steady.application.dto.response;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.ApplicationStatus;

import java.time.LocalDateTime;

public record MyApplicationSummaryResponse(
        Long applicationId,
        String steadyName,
        LocalDateTime createdAt,
        ApplicationStatus status
) {

    public static MyApplicationSummaryResponse from(Application application) {
        return new MyApplicationSummaryResponse(
                application.getId(),
                application.getSteady().getName(),
                application.getCreatedAt(),
                application.getStatus()
        );
    }

}
