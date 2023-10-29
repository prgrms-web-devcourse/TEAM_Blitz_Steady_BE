package dev.steady.steady.domain;

import dev.steady.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Participants {

    @OneToMany(mappedBy = "steady", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> steadyParticipants = new ArrayList<>();

    public void add(Participant participant) {
        steadyParticipants.add(participant);
    }

    public User getLeader() {
        return steadyParticipants.stream()
                .filter(Participant::isLeader)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .getUser();
    }

    public int getNumberOfParticipants() {
        return steadyParticipants.size();
    }

}
