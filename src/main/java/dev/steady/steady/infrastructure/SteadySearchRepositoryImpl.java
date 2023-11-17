package dev.steady.steady.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyStatus;
import dev.steady.steady.dto.SearchConditionDto;
import dev.steady.steady.dto.response.MySteadyQueryResponse;
import dev.steady.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static dev.steady.steady.domain.QParticipant.participant;
import static dev.steady.steady.domain.QSteady.steady;
import static dev.steady.steady.domain.QSteadyPosition.steadyPosition;
import static dev.steady.steady.domain.QSteadyStack.steadyStack;
import static dev.steady.steady.domain.SteadyStatus.CLOSED;
import static dev.steady.steady.domain.SteadyStatus.FINISHED;
import static dev.steady.steady.domain.SteadyStatus.RECRUITING;
import static dev.steady.steady.infrastructure.util.DynamicQueryUtils.filterCondition;
import static dev.steady.steady.infrastructure.util.DynamicQueryUtils.orderBySort;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SteadySearchRepositoryImpl implements SteadySearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Steady> findAllBySearchCondition(SearchConditionDto condition, Pageable pageable) {
        List<Steady> steadies = jpaQueryFactory
                .selectFrom(steady)
                .innerJoin(steady.steadyStacks, steadyStack)
                .innerJoin(steadyPosition)
                .on(steady.id.eq(steadyPosition.steady.id))
                .where(searchCondition(condition))
                .orderBy(orderBySort(pageable.getSort(), Steady.class))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        JPAQuery<Steady> count = jpaQueryFactory
                .selectFrom(steady)
                .innerJoin(steady.steadyStacks, steadyStack)
                .innerJoin(steadyPosition)
                .on(steady.id.eq(steadyPosition.steady.id))
                .where(searchCondition(condition))
                .distinct();
        // TODO: 2023-11-04 좋아요 (북마크) 구현된다면 where 조건 추가
        return PageableExecutionUtils.getPage(steadies, pageable, count::fetchCount);
    }

    @Override
    public Slice<MySteadyQueryResponse> findMySteadies(SteadyStatus status, User user, Pageable pageable) {
        List<MySteadyQueryResponse> steadies = jpaQueryFactory
                .select(Projections.constructor(MySteadyQueryResponse.class,
                        steady.id,
                        steady.name,
                        participant.isLeader,
                        participant.createdAt))
                .from(steady)
                .innerJoin(participant).on(participant.steady.id.eq(steady.id))
                .where(
                        isFinishSteady(status),
                        isWorkSteady(status),
                        isParticipantUserIdEqual(user)
                )
                .orderBy(orderBySort(pageable.getSort(), Participant.class), steady.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = steadies.size() > pageable.getPageSize();
        List<MySteadyQueryResponse> content = hasNext ? steadies.subList(0, steadies.size() - 1) : steadies;

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression isParticipantUserIdEqual(User user) {
        return participant.user.id.eq(user.getId());
    }

    private BooleanExpression isFinishSteady(SteadyStatus status) {
        if (FINISHED == status) {
            return steady.status.eq(FINISHED);
        }
        return null;
    }

    private BooleanExpression isWorkSteady(SteadyStatus status) {
        if (status == RECRUITING || status == CLOSED) {
            return steady.status.eq(SteadyStatus.RECRUITING)
                    .or(steady.status.eq(CLOSED));
        }
        return null;
    }

    private BooleanBuilder searchCondition(SearchConditionDto condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(filterCondition(condition.steadyType(), steady.type::eq));
        booleanBuilder.and(filterCondition(condition.steadyMode(), steady.steadyMode::eq));
        booleanBuilder.and(filterCondition(condition.stacks(), steadyStack.stack.name::in));
        booleanBuilder.and(filterCondition(condition.positions(), steadyPosition.position.name::in));
        booleanBuilder.and(filterCondition(condition.status(), steady.status::eq));
        if (StringUtils.hasText(condition.keyword())) {
            return booleanBuilder.and(filterCondition(condition.keyword(), steady.title::contains))
                    .or(filterCondition(condition.keyword(), steady.content::contains));
        }
        return booleanBuilder;
    }

}
