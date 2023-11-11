package dev.steady.user.dto.response;

import dev.steady.user.domain.Position;

public record PositionResponse(
        Long id,
        String name
) {

    public static PositionResponse from(Position position) {
        return new PositionResponse(position.getId(), position.getName());
    }

}
