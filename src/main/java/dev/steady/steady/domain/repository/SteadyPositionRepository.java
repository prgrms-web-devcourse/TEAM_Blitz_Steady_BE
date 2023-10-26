package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.SteadyPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteadyPositionRepository extends JpaRepository<SteadyPosition, Long> {
}
