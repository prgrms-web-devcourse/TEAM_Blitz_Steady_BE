package dev.steady.template.controller;

import dev.steady.global.auth.Auth;
import dev.steady.global.auth.UserInfo;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.template.dto.request.UpdateTemplateRequest;
import dev.steady.template.dto.resonse.TemplateDetailResponse;
import dev.steady.template.dto.resonse.TemplateResponses;
import dev.steady.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
                                               @Auth UserInfo userInfo) {
        templateService.createTemplate(request, userInfo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<TemplateResponses> getTemplates(@Auth UserInfo userInfo) {
        TemplateResponses templates = templateService.getTemplates(userInfo);
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateDetailResponse> getDetailTemplate(@PathVariable Long templateId,
                                                                    @Auth UserInfo userInfo) {
        TemplateDetailResponse detailTemplate = templateService.getDetailTemplate(userInfo, templateId);
        return ResponseEntity.ok(detailTemplate);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long templateId,
                                               @Auth UserInfo userInfo) {
        templateService.deleteTemplate(userInfo, templateId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{templateId}")
    public ResponseEntity<Void> updateTemplate(@PathVariable Long templateId,
                                               @RequestBody UpdateTemplateRequest request,
                                               @Auth UserInfo userInfo) {
        templateService.updateTemplate(templateId, request, userInfo);
        return ResponseEntity.ok().build();
    }

}
