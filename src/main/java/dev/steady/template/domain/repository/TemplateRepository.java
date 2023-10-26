package dev.steady.template.domain.repository;

import dev.steady.template.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    List<Template> findByUserId(Long userId);

}
