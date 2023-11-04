package dev.steady.steady.infrastructure;

import dev.steady.steady.domain.Steady;
import dev.steady.steady.dto.SearchConditionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SteadySearchRepository {

    Page<Steady> findBySearchRequest(SearchConditionDto condition, Pageable pageable);

}
