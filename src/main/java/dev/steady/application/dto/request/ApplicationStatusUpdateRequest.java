package dev.steady.application.dto.request;

import dev.steady.application.domain.ApplicationStatus;

public record ApplicationStatusUpdateRequest(ApplicationStatus status) {
}
