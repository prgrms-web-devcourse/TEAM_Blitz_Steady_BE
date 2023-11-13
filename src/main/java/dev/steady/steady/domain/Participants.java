package dev.steady.steady.domain;

import dev.steady.global.exception.InvalidStateException;
import dev.steady.global.exception.NotFoundException;
import dev.steady.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dev.steady.steady.exception.ParticipantErrorCode.PARTICIPANT_NOT_FOUND;
import static dev.steady.steady.exception.SteadyErrorCode.PARTICIPANT_LIMIT_EXCEEDED;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participants {

    @OneToMany(mappedBy = "steady", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Participant> steadyParticipants = new ArrayList<>();
    private int participantLimits;

    public Participants(int participantLimits) {
        this.participantLimits = participantLimits;
    }

    public void add(Participant participant) {
        validateParticipantLimit();
        steadyParticipants.add(participant);
    }

    public User getLeader() {
        return steadyParticipants.stream()
                .filter(Participant::isLeader)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(PARTICIPANT_NOT_FOUND))
                .getUser();
    }

    public Participant getParticipantByUserId(Long userId) {
        return steadyParticipants.stream()
                .filter(participant -> Objects.equals(participant.getUserId(), userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(PARTICIPANT_NOT_FOUND));
    }

    public List<Participant> getAllParticipants() {
        return steadyParticipants;
    }

    public int getNumberOfParticipants() {
        return steadyParticipants.size();
    }

    private void validateParticipantLimit() {
        if (participantLimits == steadyParticipants.size()) {
            throw new InvalidStateException(PARTICIPANT_LIMIT_EXCEEDED);
        }
    }

}
