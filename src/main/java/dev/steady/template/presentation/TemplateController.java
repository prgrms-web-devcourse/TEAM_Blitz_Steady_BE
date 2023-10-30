package dev.steady.template.presentation;

import dev.steady.global.auth.AuthContext;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.template.dto.request.UpdateTemplateRequest;
import dev.steady.template.dto.resonse.TemplateDetailResponse;
import dev.steady.template.dto.resonse.TemplateResponses;
import dev.steady.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                                               AuthContext authContext) {
        templateService.createTemplate(request, authContext);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<TemplateResponses> getTemplates(AuthContext authContext) {
        TemplateResponses templates = templateService.getTemplates(authContext);
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateDetailResponse> getDetailTemplate(@PathVariable Long templateId,
                                                                    AuthContext authContext) {
        TemplateDetailResponse detailTemplate = templateService.getDetailTemplate(authContext, templateId);
        return ResponseEntity.ok(detailTemplate);
    }

    @PatchMapping("/{templateId}")
    public ResponseEntity<Void> updateTemplate(@PathVariable Long templateId,
                                               @RequestBody UpdateTemplateRequest request,
                                               AuthContext authContext) {
        templateService.updateTemplate(templateId, request, authContext);
        return ResponseEntity.noContent().build();
    }

}
