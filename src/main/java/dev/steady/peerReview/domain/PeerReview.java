package dev.steady.peerReview.domain;

import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "peer_review", uniqueConstraints =
        @UniqueConstraint(name = "UniqueReviewId", columnNames = {"reviewer_id", "reviewee_id", "steady"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeerReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "steady")
    private Steady steady;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @Builder
    private PeerReview(User reviewer,
                       User reviewee,
                       Steady steady,
                       String comment,
                       boolean isPublic) {
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.steady = steady;
        this.comment = comment;
        this.isPublic = isPublic;
    }

}
