package dev.steady.application.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record ApplicationPageRequest(
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
                        direction == null ? Sort.Direction.DESC : Sort.Direction.fromString(direction),
                        SORTING_CRITERIA
                )
        );
    }

}


