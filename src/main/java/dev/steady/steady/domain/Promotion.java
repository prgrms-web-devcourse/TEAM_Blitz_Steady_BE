package dev.steady.steady.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Embeddable
public class Promotion {

    private final static int INITIAL_PROMOTION_COUNT = 3;

    @Column(nullable = false)
    private int promotionCount;

    @Column(nullable = false)
    private LocalDateTime promotedAt;

    public Promotion() {
        this.promotionCount = INITIAL_PROMOTION_COUNT;
        this.promotedAt = LocalDateTime.now();
    }

}
