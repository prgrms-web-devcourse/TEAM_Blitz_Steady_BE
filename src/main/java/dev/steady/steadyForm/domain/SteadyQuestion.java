package dev.steady.steadyForm.domain;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private SteadyForm steadyForm;

    @Builder
    private SteadyQuestion(SteadyForm steadyForm) {
        this.steadyForm = steadyForm;
    }

}
