package dev.steady.review.fixture;

import dev.steady.review.domain.Card;
import dev.steady.review.domain.Review;
import dev.steady.review.domain.UserCard;
import dev.steady.review.dto.ReviewCreateRequest;
import dev.steady.review.dto.response.UserCardResponse;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;

import java.util.List;

public class ReviewFixture {

    public static ReviewCreateRequest createReviewCreateRequest(Long revieweeId, List<Long> cardsId) {
        return new ReviewCreateRequest(
                revieweeId,
                cardsId,
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

    public static List<UserCardResponse> createUserCardResponses() {
        return List.of(
                new UserCardResponse(1L, "이 팀원은 출석을 열심히 잘 했어요!", 3L),
                new UserCardResponse(2L, "이 팀원은 스테디에 열정적이었어요!", 2L),
                new UserCardResponse(3L, "이 팀원은 지식 공유에 적극적이었어요!", 0L),
                new UserCardResponse(4L, "이 팀원과 의사소통이 잘돼요!", 0L),
                new UserCardResponse(5L, "이 팀원이랑 나중에도 같이 스테디 하고 싶어요!", 1L)
        );
    }

}
