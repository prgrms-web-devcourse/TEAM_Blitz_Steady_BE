package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.Steady;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteadyRepository extends JpaRepository<Steady, Long> {

}
