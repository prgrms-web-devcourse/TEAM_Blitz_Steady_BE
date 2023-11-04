package dev.steady.steady.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.steady.steady.domain.Steady;
import dev.steady.steady.domain.SteadyMode;
import dev.steady.steady.dto.SearchConditionDto;
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
    public Page<Steady> findBySearchRequest(SearchConditionDto condition, Pageable pageable) {
        List<Steady> steadies = jpaQueryFactory
                .selectFrom(steady)
                .join(steady.steadyStacks, steadyStack).fetchJoin()
                .join(steadyPosition).on(steady.id.eq(steadyPosition.steady.id))
                .where(getStackCondition(condition.stacks()),
                        getPositionCondition(condition.positions()),
                        getModeCondition(condition.mode()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(steady.count())
                .from(steady)
                .where(getStackCondition(condition.stacks()),
                        getPositionCondition(condition.positions()),
                        getModeCondition(condition.mode()));

        return PageableExecutionUtils.getPage(steadies, pageable, count::fetchOne);
    }

    private BooleanExpression getStackCondition(List<String> stacks) {
        return steadyStack.stack.name.in(stacks);
    }

    private BooleanExpression getPositionCondition(List<String> positions) {
        return steadyPosition.position.name.in(positions);
    }

    private BooleanExpression getModeCondition(String mode) {
        return steady.steadyMode.eq(SteadyMode.from(mode));
    }

    // TODO: 2023/11/03 각 조건별 Null 체크 등 추가 예정
}
