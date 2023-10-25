package dev.steady.template.service;

import dev.steady.global.auth.AuthContext;
import dev.steady.template.domain.Question;
import dev.steady.template.domain.Template;
import dev.steady.template.domain.repository.QuestionRepository;
import dev.steady.template.domain.repository.TemplateRepository;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.user.domain.User;
import dev.steady.user.fixture.UserFixtures;
import dev.steady.user.infrastructure.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @AfterEach
    void tearDown() {
        questionRepository.deleteAll();
        templateRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("템플릿 양식을 입력받아 템플릿을 생성한다.")
    @Test
    void createTemplateTest() {

        User user = userRepository.save(UserFixtures.createUser());
        AuthContext authContext = new AuthContext();
        authContext.setUserId(user.getId());
        List<String> questions = List.of("Q1", "Q2");
        CreateTemplateRequest request = new CreateTemplateRequest("Sample Title", questions);

        Long templateId = templateService.createTemplate(request, authContext);

        List<Question> savedQuestions = questionRepository.findAll();
        Template template = templateRepository.findById(templateId).get();

        assertAll(
                () -> assertThat(savedQuestions.size()).isEqualTo(savedQuestions.size()),
                () -> assertThat(template).isNotNull()
        );
    }

}
