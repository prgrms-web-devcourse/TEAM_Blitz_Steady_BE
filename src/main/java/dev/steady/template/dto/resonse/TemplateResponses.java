package dev.steady.template.dto.resonse;

import dev.steady.template.domain.Template;

import java.util.List;

public record TemplateResponses(
        List<TemplateResponse> templates
) {

    public static TemplateResponses from(List<Template> templates) {
        List<TemplateResponse> responses = templates.stream()
                .map(TemplateResponse::from)
                .toList();

        return new TemplateResponses(responses);
    }

}
