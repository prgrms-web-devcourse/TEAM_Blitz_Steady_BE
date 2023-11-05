package dev.steady.steady.dto.response;

import dev.steady.steady.domain.SteadyPosition;

public record SteadyPositionResponse(
        Long id,
        String name
) {

    public static SteadyPositionResponse from(SteadyPosition steadyPosition) {
        return new SteadyPositionResponse(steadyPosition.getId(), steadyPosition.getPositionName());
    }

}
