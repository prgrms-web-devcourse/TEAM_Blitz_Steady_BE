package dev.steady.review.dto.response;

import java.time.LocalDateTime;

public record ReviewDetailResponse(
        Long reviewId,
        String comment,
        boolean isPublic,
        LocalDateTime createdAt
) {
}
