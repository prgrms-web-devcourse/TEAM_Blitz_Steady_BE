package dev.steady.steady.domain.repository;

import dev.steady.global.exception.NotFoundException;
import dev.steady.steady.domain.Participant;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static dev.steady.steady.exception.ParticipantErrorCode.PARTICIPANT_NOT_FOUND;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    default Participant getById(Long participantId) {
        return findById(participantId)
                .orElseThrow(() -> new NotFoundException(PARTICIPANT_NOT_FOUND));
    }

    List<Participant> findBySteadyId(Long id);

    List<Participant> findByUser(User user);

    Participant findByUserAndSteady(User user, Steady steady);

}
