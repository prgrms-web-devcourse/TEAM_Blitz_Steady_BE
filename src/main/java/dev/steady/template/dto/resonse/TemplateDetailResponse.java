package dev.steady.template.dto.resonse;

import dev.steady.template.domain.Template;

import java.time.LocalDateTime;
import java.util.List;

public record TemplateDetailResponse(
        Long id,
        String title,
        List<String> questions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static TemplateDetailResponse from(Template template) {
        return new TemplateDetailResponse(template.getId(),
                template.getTitle(),
                template.getContents(),
                template.getCreatedAt(),
                template.getUpdatedAt());
    }

}
