package dev.steady.review.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.steady.review.dto.response.ReviewDetailResponse;
import dev.steady.review.dto.response.ReviewsBySteadyResponse;
import dev.steady.steady.domain.Participant;
import dev.steady.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static dev.steady.review.domain.QReview.review;
import static dev.steady.steady.domain.QSteady.steady;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> getPublicCommentsByRevieweeUser(User user) {
        return jpaQueryFactory.select(review.comment)
                .from(review)
                .where(revieweeEquals(user),
                        review.comment.isNotNull(),
                        isPublic())
                .orderBy(review.createdAt.desc())
                .fetch();
    }

    @Override
    public List<ReviewsBySteadyResponse> getAllReviewsByRevieweeUser(User user) {
        return jpaQueryFactory.selectFrom(steady)
                .innerJoin(review)
                .on(steady.id.eq(review.reviewee.steady.id))
                .where(revieweeEquals(user),
                        review.comment.isNotNull())
                .orderBy(steady.finishedAt.desc())
                .transform(groupBy(steady.id)
                        .list(Projections.constructor(
                                ReviewsBySteadyResponse.class,
                                steady.id,
                                steady.name,
                                list(Projections.constructor(
                                        ReviewDetailResponse.class,
                                        review.id,
                                        review.comment,
                                        review.isPublic,
                                        review.createdAt
                                ))
                        ))
                );
    }

    @Override
    public Boolean existsReviewByReviewerAndRevieweeAndSteady(Participant reviewer, Participant reviewee, Long steadyId) {
        return jpaQueryFactory.from(review)
                       .where(revieweeEquals(reviewee),
                               reviewerEquals(reviewer),
                               steadyIdEquals(steadyId))
                       .fetchFirst() != null;
    }

    private BooleanExpression revieweeEquals(User user) {
        return review.reviewee.user.eq(user);
    }

    private BooleanExpression revieweeEquals(Participant reviewee) {
        return review.reviewee.eq(reviewee);
    }

    private BooleanExpression reviewerEquals(Participant reviewer) {
        return review.reviewer.eq(reviewer);
    }

    private BooleanExpression steadyIdEquals(Long steadyId) {
        return review.reviewer.steady.id.eq(steadyId);
    }

    private BooleanExpression isPublic() {
        return review.isPublic.isTrue();
    }

}
