package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.SteadyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SteadyQuestionRepository extends JpaRepository<SteadyQuestion, Long> {

    List<SteadyQuestion> findBySteadyId(Long id);

    void deleteBySteadyId(Long id);

}
