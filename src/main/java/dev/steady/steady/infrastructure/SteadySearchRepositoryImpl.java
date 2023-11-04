package dev.steady.steady.infrastructure;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.dto.SearchConditionDto;
import dev.steady.steady.infrastructure.util.DynamicQueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.steady.steady.domain.QSteady.steady;
import static dev.steady.steady.domain.QSteadyPosition.steadyPosition;
import static dev.steady.steady.domain.QSteadyStack.steadyStack;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SteadySearchRepositoryImpl implements SteadySearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Steady> findAllByCondition(SearchConditionDto condition, Pageable pageable) {
        List<Steady> steadies = jpaQueryFactory
                .selectFrom(steady)
                .join(steady.steadyStacks, steadyStack).fetchJoin()
                .join(steadyPosition).on(steady.id.eq(steadyPosition.steady.id))
                .where(
                        DynamicQueryUtils.equalCompare(condition.steadyMode(), steady.steadyMode::eq),
                        DynamicQueryUtils.filterCondition(condition.stacks(), steadyStack.stack.name::in),
                        DynamicQueryUtils.filterCondition(condition.positions(), steadyPosition.position.name::in),
                        DynamicQueryUtils.equalCompare(condition.status(), steady.status::eq),
                        DynamicQueryUtils.filterCondition(condition.keyword(), steady.title::contains)
                                .or(DynamicQueryUtils.filterCondition(condition.keyword(), steady.content::contains))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(steady.count())
                .from(steady)
                .where(
                        DynamicQueryUtils.equalCompare(condition.steadyMode(), steady.steadyMode::eq),
                        DynamicQueryUtils.filterCondition(condition.stacks(), steadyStack.stack.name::in),
                        DynamicQueryUtils.filterCondition(condition.positions(), steadyPosition.position.name::in),
                        DynamicQueryUtils.equalCompare(condition.status(), steady.status::eq),
                        DynamicQueryUtils.filterCondition(condition.keyword(), steady.title::contains)
                                .or(DynamicQueryUtils.filterCondition(condition.keyword(), steady.content::contains))
                );

        // TODO: 2023-11-04 좋아요 (북마크) 구현된다면 where 조건 추가
        return PageableExecutionUtils.getPage(steadies, pageable, count::fetchOne);
    }

}
