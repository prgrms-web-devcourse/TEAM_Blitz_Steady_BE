package dev.steady.steady.domain.repository;

import dev.steady.steady.domain.SteadyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteadyQuestionRepository extends JpaRepository<SteadyQuestion, Long> {

}
