package dev.steady.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "review_stickers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sticker sticker;

    @Builder
    private ReviewSticker(Review review, Sticker sticker) {
        this.review = review;
        this.sticker = sticker;
    }

}
