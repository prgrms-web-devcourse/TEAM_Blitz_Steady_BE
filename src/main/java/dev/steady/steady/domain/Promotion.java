package dev.steady.steady.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Embeddable
public class Promotion {

    private final static int INITIAL_PROMOTION_COUNT = 3;

    @Column(name = "promotion_count", nullable = false)
    private int promotion_count;

    @Column(name = "promoted_at", nullable = false)
    private LocalDateTime promoted_at;

    public Promotion() {
        this.promotion_count = INITIAL_PROMOTION_COUNT;
        this.promoted_at = LocalDateTime.now();
    }

}
