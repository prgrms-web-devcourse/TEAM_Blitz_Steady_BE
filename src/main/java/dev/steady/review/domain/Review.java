package dev.steady.review.domain;

import dev.steady.global.entity.BaseEntity;
import dev.steady.steady.domain.Participant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private Participant reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id")
    private Participant reviewee;

    @Column
    private String comment;

    @Column(nullable = false)
    private boolean isPublic;

    @Builder
    private Review(Participant reviewer,
                   Participant reviewee,
                   String comment) {
        this.reviewer = reviewer;
        this.reviewee = reviewee;
        this.comment = comment;
        this.isPublic = true;
    }

    public void switchIsPublic() {
        this.isPublic = !isPublic;
    }

}
