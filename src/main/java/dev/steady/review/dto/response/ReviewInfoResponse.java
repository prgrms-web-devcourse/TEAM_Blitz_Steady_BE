package dev.steady.review.dto.response;

import java.util.List;

public record ReviewInfoResponse(
        ReviewSteadyResponse steady,
        List<RevieweeResponse> reviewees
) {

    public static ReviewInfoResponse of(ReviewSteadyResponse steady,
                                        List<RevieweeResponse> reviewees) {
        return new ReviewInfoResponse(steady, reviewees);
    }

}
