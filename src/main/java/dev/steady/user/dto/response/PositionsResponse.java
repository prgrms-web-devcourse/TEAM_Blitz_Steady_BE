package dev.steady.user.dto.response;

import dev.steady.user.domain.Position;

import java.util.List;

public record PositionsResponse(
        List<PositionResponse> positions
) {

    public static PositionsResponse from(List<Position> positions) {
        List<PositionResponse> responses = positions.stream()
                .map(PositionResponse::from)
                .toList();

        return new PositionsResponse(responses);
    }

}
