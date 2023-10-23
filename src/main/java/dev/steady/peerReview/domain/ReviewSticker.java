package dev.steady.peerReview.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "review_sticker")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PeerReview peerReview;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sticker sticker;

    @Builder
    private ReviewSticker(PeerReview peerReview, Sticker sticker) {
        this.peerReview = peerReview;
        this.sticker = sticker;
    }

}
