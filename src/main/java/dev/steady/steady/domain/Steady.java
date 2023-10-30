package dev.steady.steady.domain;

import dev.steady.global.entity.BaseEntity;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "steadies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Steady extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SteadyType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SteadyStatus status;

    @Column(nullable = false)
    private int participantLimit;

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

    @Embedded
    private Participants participants;

    @OneToMany(mappedBy = "steady", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SteadyStack> steadyStacks = new ArrayList<>();

    @Builder
    private Steady(String name,
                   String bio,
                   SteadyType type,
                   int participantLimit,
                   SteadyMode steadyMode,
                   LocalDate openingDate,
                   LocalDate deadline,
                   String title,
                   String content,
                   User user,
                   Promotion promotion,
                   List<Stack> stacks) {
        this.participants = createParticipants(user);
        this.numberOfParticipants = participants.getNumberOfParticipants();
        this.name = name;
        this.bio = bio;
        this.type = type;
        this.status = SteadyStatus.RECRUITING;
        this.participantLimit = participantLimit;
        this.steadyMode = steadyMode;
        this.openingDate = openingDate;
        this.deadline = deadline;
        this.title = title;
        this.content = content;
        this.promotion = promotion;
        this.steadyStacks = createSteadyStack(stacks);
    }

    public boolean isLeader(Long userId) {
        Long leaderId = participants.getLeader().getId();
        if (leaderId.equals(userId)) {
            return true;
        }
        return false;
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void update(String name,
                       String bio,
                       SteadyType type,
                       SteadyStatus status,
                       int participantLimit,
                       SteadyMode steadyMode,
                       LocalDate openingDate,
                       LocalDate deadline,
                       String title,
                       String content,
                       List<Stack> stacks) {
        this.name = name;
        this.bio = bio;
        this.type = type;
        this.status = status;
        this.participantLimit = participantLimit;
        this.steadyMode = steadyMode;
        this.openingDate = openingDate;
        this.deadline = deadline;
        this.title = title;
        this.content = content;
        this.steadyStacks.clear();
        this.steadyStacks.addAll(createSteadyStack(stacks));
    }

    private Participants createParticipants(User user) {
        Participants participants = new Participants();
        participants.add(Participant.createLeader(user, this));
        return participants;
    }

    private List<SteadyStack> createSteadyStack(List<Stack> stacks) {
        return stacks.stream()
                .map(stack -> new SteadyStack(stack, this))
                .collect(Collectors.toList());
    }

}
