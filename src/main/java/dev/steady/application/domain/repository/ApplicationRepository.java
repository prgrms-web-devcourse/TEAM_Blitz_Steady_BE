package dev.steady.application.domain.repository;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.ApplicationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findBySteadyIdAndUserIdAndStatus(Long steadyId, Long userId, ApplicationStatus status);

    Slice<Application> findAllBySteadyId(Long steadyId, Pageable pageable);

    default Application getById(Long applicationId) {
        return findById(applicationId)
                .orElseThrow(IllegalArgumentException::new);
    }

}
