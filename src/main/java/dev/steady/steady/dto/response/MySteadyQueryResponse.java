package dev.steady.steady.dto.response;

import java.time.LocalDateTime;

public record MySteadyQueryResponse(
        Long steadyId,
        String name,
        String contact,
        boolean isLeader,
        LocalDateTime joinedAt
) {
}
