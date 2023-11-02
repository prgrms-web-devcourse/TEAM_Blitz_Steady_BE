package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.SteadyPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SteadyPositionRepository extends JpaRepository<SteadyPosition, Long> {

    List<SteadyPosition> findBySteadyId(Long id);

    void deleteBySteadyId(Long id);

}
