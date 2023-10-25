package dev.steady.template.service;

import dev.steady.global.auth.AuthContext;
import dev.steady.template.domain.Question;
import dev.steady.template.domain.Template;
import dev.steady.template.domain.repository.QuestionRepository;
import dev.steady.template.domain.repository.TemplateRepository;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.user.domain.User;
import dev.steady.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Long createTemplate(CreateTemplateRequest request, AuthContext auth) {
        User user = userRepository.getUserBy(auth.getUserId());

        Template template = new Template(user, request.title());
        Template savedTemplate = templateRepository.save(template);
        createQuestions(savedTemplate, request.questions());

        return savedTemplate.getId();
    }

    private List<Question> createQuestions(Template template, List<String> questions) {
        return questions.stream()
                .map(question -> questionRepository.save(new Question(template, question)))
                .toList();
    }

}
