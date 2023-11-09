package dev.steady.steady.domain;

import dev.steady.steady.exception.PromotionCountException;
import dev.steady.steady.exception.SteadyErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

import static dev.steady.steady.exception.SteadyErrorCode.PROMOTION_COUNT_EXCEPTION;

@Getter
@Embeddable
public class Promotion {

    private static final int INITIAL_PROMOTION_COUNT = 3;

    @Column(nullable = false)
    private int promotionCount;

    @Column(nullable = false)
    private LocalDateTime promotedAt;

    public Promotion() {
        this.promotionCount = INITIAL_PROMOTION_COUNT;
        this.promotedAt = LocalDateTime.now();
    }

    public void use() {
        validatePromotionCount();
        this.promotionCount--;
        this.promotedAt = LocalDateTime.now();
    }

    private void validatePromotionCount() {
        if (promotionCount < 0) {
            throw new PromotionCountException(PROMOTION_COUNT_EXCEPTION);
        }
    }

}
