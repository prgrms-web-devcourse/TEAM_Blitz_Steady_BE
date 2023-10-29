package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findBySteadyId(Long id);

}
