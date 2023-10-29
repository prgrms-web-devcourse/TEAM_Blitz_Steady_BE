package dev.steady.steady.domain;

import dev.steady.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "steady_questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SteadyQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "steady_id")
    private Steady steady;

    @Builder
    private SteadyQuestion(String content, int sequence, Steady steady) {
        this.content = content;
        this.sequence = sequence;
        this.steady = steady;
    }

}
