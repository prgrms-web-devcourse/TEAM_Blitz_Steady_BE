package dev.steady.application.domain.repository;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.SurveyResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {

    List<SurveyResult> findByApplicationOrderBySequenceAsc(Application application);

}
