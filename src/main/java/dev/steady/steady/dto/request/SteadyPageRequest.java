package dev.steady.steady.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public record SteadyPageRequest(
        Integer page,
        String direction
) {

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 10;
    public static final String SORTING_CRITERIA = "createdAt";

    public Pageable toPageable() {
        return PageRequest.of(
                page == null ? DEFAULT_PAGE : page,
                DEFAULT_SIZE,
                Sort.by(
                        direction == null ? Direction.DESC : Direction.fromString(direction),
                        SORTING_CRITERIA
                )
        );
    }

}
