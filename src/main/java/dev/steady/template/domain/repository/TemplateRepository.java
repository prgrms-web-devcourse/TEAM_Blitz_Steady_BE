package dev.steady.template.domain.repository;

import dev.steady.template.domain.Template;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    default Template getById(@NotNull Long templateId) {
        return findById(templateId)
                .orElseThrow(IllegalArgumentException::new);
    }

    List<Template> findByUserId(Long userId);

}
