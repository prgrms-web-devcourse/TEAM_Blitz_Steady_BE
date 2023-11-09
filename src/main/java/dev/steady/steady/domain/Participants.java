package dev.steady.steady.domain;

import dev.steady.steady.exception.ParticipantLimitExceededException;
import dev.steady.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static dev.steady.steady.exception.SteadyErrorCode.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participants {

    private int participantLimits;

    public Participants(int participantLimits) {
        this.participantLimits = participantLimits;
    }

    @OneToMany(mappedBy = "steady", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> steadyParticipants = new ArrayList<>();

    public void add(Participant participant) {
        validateParticipantLimit();
        steadyParticipants.add(participant);
    }

    public User getLeader() {
        return steadyParticipants.stream()
                .filter(Participant::isLeader)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .getUser();
    }

    public List<Participant> getAllParticipants() {
        return steadyParticipants;
    }

    public int getNumberOfParticipants() {
        return steadyParticipants.size();
    }

    private void validateParticipantLimit() {
        if (participantLimits == steadyParticipants.size()) {
            throw new ParticipantLimitExceededException(PARTICIPANT_LIMIT_EXCEEDED);
        }
    }

}
