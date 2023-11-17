package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.SteadyViewLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewCountLogRepository extends JpaRepository<SteadyViewLog, Long> {

    Optional<SteadyViewLog> findFirstByUserIdAndSteadyIdOrderByCreatedAtDesc(Long userId, Long steadyId);

}
