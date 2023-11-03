package dev.steady.application.dto.response;

import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponse<T>(
        List<T> content,
        long numberOfElements,
        boolean hasNext
) {

    public static <T> SliceResponse<T> from(Slice<T> slice) {
        return new SliceResponse<>(
                slice.getContent(),
                slice.getNumberOfElements(),
                slice.hasNext());
    }

}
