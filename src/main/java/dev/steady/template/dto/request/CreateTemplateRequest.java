package dev.steady.template.dto.request;

import java.util.List;

public record CreateTemplateRequest(String title, List<String> questions) {
}
