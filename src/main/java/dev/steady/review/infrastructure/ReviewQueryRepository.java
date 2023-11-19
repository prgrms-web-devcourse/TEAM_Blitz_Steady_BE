package dev.steady.review.infrastructure;

import dev.steady.user.domain.User;

import java.util.List;

public interface ReviewQueryRepository {

    List<String> findPublicCommentsByRevieweeUser(User user);
    List<String> getPublicCommentsByRevieweeUser(User user);

}
