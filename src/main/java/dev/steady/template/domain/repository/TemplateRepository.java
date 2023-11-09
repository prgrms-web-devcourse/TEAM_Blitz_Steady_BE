package dev.steady.template.domain.repository;

import dev.steady.global.exception.ForbiddenException;
import dev.steady.global.exception.NotFoundException;
import dev.steady.template.domain.Template;
import dev.steady.template.exception.TemplateErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static dev.steady.template.exception.TemplateErrorCode.TEMPLATE_NOT_FOUND;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findById(Long templateId);

    default Template getById(Long templateId) {
        return findById(templateId)
                .orElseThrow(() -> new NotFoundException(TEMPLATE_NOT_FOUND));
    }

    List<Template> findByUserId(Long userId);

}
