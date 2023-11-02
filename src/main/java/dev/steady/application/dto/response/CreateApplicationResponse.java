package dev.steady.application.dto.response;

import dev.steady.application.domain.Application;

public record CreateApplicationResponse(Long applicationId) {

    public static CreateApplicationResponse from(Application application) {
        return new CreateApplicationResponse(application.getId());
    }

}
