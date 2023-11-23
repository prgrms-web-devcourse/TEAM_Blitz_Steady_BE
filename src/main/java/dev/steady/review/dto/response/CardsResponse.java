package dev.steady.review.dto.response;

import dev.steady.review.domain.Card;

import java.util.List;

public record CardsResponse(
        List<CardResponse> cards
) {

    public static CardsResponse from(List<Card> cards) {
        return new CardsResponse(
                cards.stream().map(CardResponse::from).toList()
        );
    }

}
