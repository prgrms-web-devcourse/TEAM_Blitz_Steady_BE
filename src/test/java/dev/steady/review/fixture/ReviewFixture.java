package dev.steady.review.fixture;

import dev.steady.review.domain.Card;
import dev.steady.review.domain.Review;
import dev.steady.review.domain.UserCard;
import dev.steady.review.dto.ReviewCreateRequest;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;

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

    public static Review createReview(Participant reviewer, Participant reviewee, Steady steady) {
        return Review.builder()
                .steady(steady)
                .reviewer(reviewer)
                .reviewee(reviewee)
                .comment("다음에도 저랑 같이 공부해요!")
                .build();
    }

    public static UserCard createUserCard(User user, Card card) {
        return new UserCard(user, card);
    }

}
