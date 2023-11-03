package dev.steady.steady.infrastructure;

import dev.steady.steady.domain.Steady;
import dev.steady.steady.dto.SearchKeywordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SteadyQueryDslRepository {

    Page<Steady> findBySearchRequest(SearchKeywordDto keywordDto, Pageable pageable);

}
