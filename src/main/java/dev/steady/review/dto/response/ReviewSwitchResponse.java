package dev.steady.review.dto.response;

import dev.steady.review.domain.Review;

public record ReviewSwitchResponse(
        boolean isPublic
) {

    public static ReviewSwitchResponse from(Review review) {
        return new ReviewSwitchResponse(review.isPublic());
    }
}
