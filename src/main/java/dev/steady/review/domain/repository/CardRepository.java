package dev.steady.review.domain.repository;

import dev.steady.global.exception.NotFoundException;
import dev.steady.review.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import static dev.steady.review.exception.ReviewErrorCode.CARD_NOT_FOUND;

public interface CardRepository extends JpaRepository<Card, Long> {

    default Card getById(Long cardId) {
        return findById(cardId)
                .orElseThrow(() -> new NotFoundException(CARD_NOT_FOUND));
    }

}
