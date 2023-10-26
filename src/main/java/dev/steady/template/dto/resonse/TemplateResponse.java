package dev.steady.template.dto.resonse;

import dev.steady.template.domain.Template;

import java.time.LocalDateTime;

public record TemplateResponse(Long id, String title, LocalDateTime createdAt) {

    public static TemplateResponse from(Template template) {
        return new TemplateResponse(template.getId(),
                template.getTitle(),
                template.getCreatedAt());
    }

}
