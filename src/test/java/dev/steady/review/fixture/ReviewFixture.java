package dev.steady.review.fixture;

import dev.steady.review.domain.Card;
import dev.steady.review.dto.ReviewCreateRequest;

import java.util.List;

public class ReviewFixture {

    public static ReviewCreateRequest createReviewCreateRequest(Long revieweeId, List<Long> cardIds) {
        return new ReviewCreateRequest(
                revieweeId,
                cardIds,
                "리뷰 한 줄 코멘트"
        );
    }

    public static Card createCard() {
        return new Card(
                "이 팀원은 출석을 열심히 잘 했어요!"
        );
    }

}
