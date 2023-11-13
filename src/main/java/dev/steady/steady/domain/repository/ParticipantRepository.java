package dev.steady.steady.domain.repository;

import dev.steady.global.exception.NotFoundException;
import dev.steady.steady.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static dev.steady.steady.exception.ParticipantErrorCode.PARTICIPANT_NOT_FOUND;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findBySteadyId(Long id);

    default Participant getById(Long participantId) {
        return findById(participantId)
                .orElseThrow(() -> new NotFoundException(PARTICIPANT_NOT_FOUND));
    }

}
