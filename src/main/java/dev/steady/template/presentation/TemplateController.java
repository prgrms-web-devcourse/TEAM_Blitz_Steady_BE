package dev.steady.template.presentation;

import dev.steady.global.auth.AuthContext;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/template")
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<Void> createTemplate(@RequestBody CreateTemplateRequest request,
                                               AuthContext auth) {
        templateService.createTemplate(request, auth);

        return ResponseEntity.noContent().build();
    }

}
