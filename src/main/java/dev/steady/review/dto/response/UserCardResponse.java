package dev.steady.review.dto.response;

public record UserCardResponse(
        Long cardId,
        String imageUrl,
        Long count
) {
}
