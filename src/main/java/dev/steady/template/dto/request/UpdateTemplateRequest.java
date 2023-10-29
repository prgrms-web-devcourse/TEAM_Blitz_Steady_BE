package dev.steady.template.dto.request;

import java.util.List;

public record UpdateTemplateRequest(
        String title,
        List<String> questions
) {

}
