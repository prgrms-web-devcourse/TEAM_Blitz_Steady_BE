package dev.steady.review.dto.response;

import java.util.List;

public record ReviewMyResponse(
        List<UserCardResponse> userCards,
        List<ReviewsBySteadyResponse> reviews
) {

    public static ReviewMyResponse of(List<UserCardResponse> userCards,
                                      List<ReviewsBySteadyResponse> reviews) {
        return new ReviewMyResponse(userCards, reviews);
    }

}
