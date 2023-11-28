package dev.steady.steady.dto.response;

import java.time.LocalDateTime;

public record MySteadyResponse(
        Long steadyId,
        String name,
        String contact,
        boolean isLeader,
        LocalDateTime joinedAt
) {

    public static MySteadyResponse from(MySteadyQueryResponse response) {
        return new MySteadyResponse(response.steadyId(),
                response.name(),
                response.contact(),
                response.isLeader(),
                response.joinedAt());
    }

}
