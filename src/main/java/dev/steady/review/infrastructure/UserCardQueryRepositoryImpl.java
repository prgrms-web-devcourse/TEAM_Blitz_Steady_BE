package dev.steady.review.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.steady.review.dto.response.UserCardResponse;
import dev.steady.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.steady.review.domain.QCard.card;
import static dev.steady.review.domain.QUserCard.userCard;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCardQueryRepositoryImpl implements UserCardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserCardResponse> getCardCountByUser(User user) {
        return jpaQueryFactory.select(Projections.constructor(UserCardResponse.class,
                        card.id,
                        card.imageUrl,
                        userCard.card.count()))
                .from(card)
                .leftJoin(userCard)
                .on(card.eq(userCard.card),
                        userCard.user.eq(user))
                .groupBy(card.id)
                .orderBy(card.id.asc())
                .fetch();
    }

}
