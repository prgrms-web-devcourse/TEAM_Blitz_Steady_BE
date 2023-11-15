package dev.steady.review.dto.response;

public record UserCardResponse(
        Long cardId,
        String content,
        Long count
) {
}
