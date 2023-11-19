package dev.steady.review.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.steady.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.steady.review.domain.QReview.review;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> getPublicCommentsByRevieweeUser(User user) {
        return jpaQueryFactory.select(review.comment)
                .from(review)
                .where(revieweeEqualsUser(user),
                        isPublic())
                .orderBy(review.createdAt.desc())
                .fetch();
    }

    private BooleanExpression revieweeEqualsUser(User user) {
        return review.reviewee.user.eq(user);
    }

    private BooleanExpression isPublic() {
        return review.isPublic.isTrue();
    }

}
