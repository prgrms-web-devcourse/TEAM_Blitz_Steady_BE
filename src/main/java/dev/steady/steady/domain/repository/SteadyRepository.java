package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.Steady;
import dev.steady.steady.infrastructure.SteadyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteadyRepository extends JpaRepository<Steady, Long>, SteadyRepositoryCustom {

    default Steady getSteady(Long steadyId) {
        return findById(steadyId)
                .orElseThrow(IllegalArgumentException::new);
    }

}
