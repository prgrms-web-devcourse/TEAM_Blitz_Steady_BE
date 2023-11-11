package dev.steady.steady.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.dto.SearchConditionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static dev.steady.steady.domain.QSteady.steady;
import static dev.steady.steady.domain.QSteadyPosition.steadyPosition;
import static dev.steady.steady.domain.QSteadyStack.steadyStack;
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
                .join(steady.steadyStacks, steadyStack).fetchJoin()
                .join(steadyPosition).on(steady.id.eq(steadyPosition.steady.id))
                .where(searchCondition(condition))
                .orderBy(orderBySort(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(steady.count())
                .from(steady)
                .where(searchCondition(condition));
        // TODO: 2023-11-04 좋아요 (북마크) 구현된다면 where 조건 추가
        return PageableExecutionUtils.getPage(steadies, pageable, count::fetchOne);
    }

    private BooleanBuilder searchCondition(SearchConditionDto condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
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
