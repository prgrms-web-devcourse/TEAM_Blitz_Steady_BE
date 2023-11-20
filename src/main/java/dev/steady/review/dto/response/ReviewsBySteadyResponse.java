package dev.steady.review.dto.response;

import java.util.List;

public record ReviewsBySteadyResponse(
        Long steadyId,
        String steadyName,
        List<ReviewDetailResponse> reviews
) {
}
