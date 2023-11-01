package dev.steady.template.domain.repository;

import dev.steady.template.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findById(Long templateId);

    default Template getById(Long templateId) {
        return findById(templateId)
                .orElseThrow(IllegalArgumentException::new);
    }

    List<Template> findByUserId(Long userId);

}
