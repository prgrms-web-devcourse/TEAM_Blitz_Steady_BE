package dev.steady.review.dto.response;

import java.util.List;

public record ReviewMyResponse(
        List<UserCardResponse> cards,
        List<ReviewsBySteadyResponse> reviews
) {
    
    public static ReviewMyResponse of(List<UserCardResponse> cards,
                                      List<ReviewsBySteadyResponse> reviews) {
        return new ReviewMyResponse(cards, reviews);
    }

}
