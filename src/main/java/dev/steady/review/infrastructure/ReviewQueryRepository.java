package dev.steady.review.infrastructure;

import dev.steady.review.dto.response.ReviewsBySteadyResponse;
import dev.steady.steady.domain.Participant;
import dev.steady.user.domain.User;

import java.util.List;

public interface ReviewQueryRepository {

    List<String> getPublicCommentsByRevieweeUser(User user);

    List<ReviewsBySteadyResponse> getAllReviewsByRevieweeUser(User user);

    Boolean existsReviewByReviewerAndRevieweeAndSteady(Participant reviewer, Participant reviewee, Long steadyId);

}
