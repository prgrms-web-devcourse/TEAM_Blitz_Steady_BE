package dev.steady.review.domain.repository;

import dev.steady.review.domain.Review;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByReviewerAndRevieweeAndSteady(Participant reviewer, Participant reviewee, Steady steady);
}
