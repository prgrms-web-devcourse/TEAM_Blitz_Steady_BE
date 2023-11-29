package dev.steady.review.domain.repository;

import dev.steady.global.exception.NotFoundException;
import dev.steady.review.domain.Review;
import dev.steady.review.infrastructure.ReviewQueryRepository;
import dev.steady.steady.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import static dev.steady.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewQueryRepository {

    default Review getById(Long reviewId) {
        return findById(reviewId)
                .orElseThrow(() -> new NotFoundException(REVIEW_NOT_FOUND));
    }

    void deleteAllByReviewee(Participant participant);

}
