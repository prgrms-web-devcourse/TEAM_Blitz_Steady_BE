package dev.steady.template.service;

import dev.steady.global.auth.UserInfo;
import dev.steady.template.domain.Template;
import dev.steady.template.domain.repository.TemplateRepository;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.template.dto.request.UpdateTemplateRequest;
import dev.steady.template.dto.resonse.TemplateDetailResponse;
import dev.steady.template.dto.resonse.TemplateResponses;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createTemplate(CreateTemplateRequest request, UserInfo userInfo) {
        User user = userRepository.getUserBy(userInfo.userId());

        Template template = Template.create(user, request.title(), request.questions());
        Template savedTemplate = templateRepository.save(template);

        return savedTemplate.getId();
    }

    @Transactional(readOnly = true)
    public TemplateResponses getTemplates(UserInfo userInfo) {
        User user = userRepository.getUserBy(userInfo.userId());

        List<Template> templates = templateRepository.findByUserId(user.getId());
        return TemplateResponses.from(templates);
    }

    @Transactional(readOnly = true)
    public TemplateDetailResponse getDetailTemplate(UserInfo userInfo, Long templateId) {
        User user = userRepository.getUserBy(userInfo.userId());

        Template template = templateRepository.getById(templateId);
        template.validateOwner(user);
        return TemplateDetailResponse.from(template);
    }

    @Transactional
    public void deleteTemplate(UserInfo userInfo, Long templateId) {
        User user = userRepository.getUserBy(userInfo.userId());

        Template template = templateRepository.getById(templateId);
        template.validateOwner(user);
        templateRepository.delete(template);
    }

    @Transactional
    public void updateTemplate(Long templateId, UpdateTemplateRequest request, UserInfo userInfo) {
        User user = userRepository.getUserBy(userInfo.userId());
        Template template = templateRepository.getById(templateId);

        template.update(user, request.title(), request.questions());
    }

}
