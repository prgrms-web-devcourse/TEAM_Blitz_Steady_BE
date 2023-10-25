package dev.steady.steady.domain;

import dev.steady.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "steadies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Steady extends BaseEntity {

    private final static int INITIAL_NUMBER_OF_MEMBER = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SteadyType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SteadyStatus status;

    @Column(nullable = false)
    private int recruitCount;

    @Column(nullable = false)
    private int numberOfParticipants;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SteadyMode steadyMode;

    @Column(nullable = false)
    private LocalDate openingDate;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Embedded
    private Promotion promotion;

    @OneToMany(mappedBy = "steady", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();

    @Builder
    private Steady(String name,
                   SteadyType type,
                   int recruitCount,
                   SteadyMode steadyMode,
                   LocalDate openingDate,
                   LocalDate deadline,
                   String title,
                   String content,
                   Promotion promotion) {
        this.name = name;
        this.type = type;
        this.status = SteadyStatus.RECRUITING;
        this.recruitCount = recruitCount;
        this.numberOfParticipants = INITIAL_NUMBER_OF_MEMBER;
        this.steadyMode = steadyMode;
        this.openingDate = openingDate;
        this.deadline = deadline;
        this.title = title;
        this.content = content;
        this.promotion = promotion;
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

}
