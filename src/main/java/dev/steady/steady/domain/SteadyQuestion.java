package dev.steady.steady.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "steady_questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SteadyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "steady_id")
    private Steady steady;

    @Builder
    private SteadyQuestion(String content, int order, Steady steady) {
        this.content = content;
        this.order = order;
        this.steady = steady;
    }

}
