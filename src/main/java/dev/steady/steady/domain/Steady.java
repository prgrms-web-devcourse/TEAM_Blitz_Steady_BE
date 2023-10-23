package dev.steady.steady.domain;

import dev.steady.form.domain.Form;
import dev.steady.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "steadies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Steady extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SteadyType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SteadyStatus status;

    @Column(name = "recruit_count", nullable = false)
    private int recruit_count;

    @Enumerated(EnumType.STRING)
    @Column(name = "steady_mode", nullable = false)
    private SteadyMode steadyMode;

    @Column(name = "estimate", nullable = false)
    private LocalDateTime estimate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Embedded
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Form formId;

    @OneToMany(mappedBy = "steady", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;

    @Builder
    private Steady(String name,
                   SteadyType type,
                   SteadyStatus status,
                   int recruit_count,
                   SteadyMode steadyMode,
                   LocalDateTime estimate,
                   String title,
                   String content,
                   LocalDateTime deadline,
                   Promotion promotion,
                   Form formId,
                   List<Participant> participants) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.recruit_count = recruit_count;
        this.steadyMode = steadyMode;
        this.estimate = estimate;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.promotion = promotion;
        this.formId = formId;
        this.participants = participants;
    }
}
