package dev.steady.review.dto;

import dev.steady.review.domain.Review;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;

import java.util.List;

public record ReviewCreateRequest(
        Long reviewerId,
        Long revieweeId,
        List<Long> cardIds,
        String comment
) {

    public Review toEntity(Participant reviewer, Participant reviewee, Steady steady) {
        return Review.builder()
                .steady(steady)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .comment(comment)
                .build();
    }

}
