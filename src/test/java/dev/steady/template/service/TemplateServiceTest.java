package dev.steady.template.service;

import dev.steady.global.auth.AuthFixture;
import dev.steady.template.domain.Template;
import dev.steady.template.domain.repository.QuestionRepository;
import dev.steady.template.domain.repository.TemplateRepository;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.template.dto.request.UpdateTemplateRequest;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.fixture.UserFixtures;
import dev.steady.user.infrastructure.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static dev.steady.template.fixture.TemplateFixture.createAnotherTemplate;
import static dev.steady.template.fixture.TemplateFixture.createTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TransactionTemplate tm;

    @AfterEach
    void tearDown() {
        questionRepository.deleteAll();
        templateRepository.deleteAll();
        userRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @DisplayName("템플릿 양식을 입력받아 템플릿을 생성한다.")
    @Test
    void createTemplateTest() {
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = userRepository.save(UserFixtures.createUser(position));
        var authContext = AuthFixture.createAuthContext(user.getId());
        var questions = List.of("Q1", "Q2");
        var request = new CreateTemplateRequest("Sample Title", questions);

        var templateId = templateService.createTemplate(request, authContext);

        var savedQuestions = questionRepository.findAll();
        var template = templateRepository.findById(templateId).get();

        assertAll(
                () -> assertThat(template).isNotNull(),
                () -> assertThat(savedQuestions.size()).isEqualTo(questions.size())
        );
    }

    @DisplayName("사용자의 계정 정보를 받아 템플릿 목록을 조회한다.")
    @Test
    void getTemplatesTest(){
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = UserFixtures.createUser(position);
        var savedUser = userRepository.save(user);

        var template1 = createTemplate(savedUser);
        var template2 = createAnotherTemplate(savedUser);
        templateRepository.saveAll(List.of(template1, template2));

        var authContext = AuthFixture.createAuthContext(savedUser.getId());
        var responses = templateService.getTemplates(authContext);

        assertThat(responses.templates()).hasSize(2)
                .extracting("title")
                .containsExactlyInAnyOrder(template1.getTitle(), template2.getTitle());
    }

    @DisplayName("템플릿 식별자를 통해 템플릿을 상세조회한다.")
    @Test
    void getDetailTemplateTest() {
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = UserFixtures.createUser(position);
        var savedUser = userRepository.save(user);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);

        var authContext = AuthFixture.createAuthContext(savedUser.getId());
        var response = templateService.getDetailTemplate(authContext, savedTemplate.getId());

        assertThat(response)
                .extracting("id", "title", "questions")
                .containsExactly(savedTemplate.getId(),
                        savedTemplate.getTitle(),
                        savedTemplate.getContents());
    }

    @DisplayName("템플릿 식별자를 통해 템플릿을 상세조회할 때 작성자가 아니라면 예외가 발생한다.")
    @Test
    void getDetailTemplateFailTest() {
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = UserFixtures.createUser(position);
        var savedUser = userRepository.save(user);
        var anotherUser = UserFixtures.createAnotherUser(position);
        userRepository.save(anotherUser);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);

        var authContext = AuthFixture.createAuthContext(anotherUser.getId());

        assertThatThrownBy(() -> templateService.getDetailTemplate(authContext, savedTemplate.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("템플릿 수정 요청을 받아서 기존 템플릿을 업데이트한다.")
    @Test
    void updateTemplateTest() {
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = UserFixtures.createUser(position);
        var savedUser = userRepository.save(user);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);

        List<String> questions = List.of("변경된 질문1", "변경된 질문2", "변경된 질문3");
        String title = "변경된 제목";
        UpdateTemplateRequest request = new UpdateTemplateRequest(title, questions);
        templateService.updateTemplate(savedTemplate.getId(), request, AuthFixture.createAuthContext(savedUser.getId()));

        Template updatedTemplate = tm.execute(status -> {
            Template findTemplate = templateRepository.findById(savedTemplate.getId()).get();
            findTemplate.getContents();
            return findTemplate;
        });
        Assertions.assertAll(
                () -> assertThat(updatedTemplate.getTitle()).isEqualTo(title),
                () -> assertThat(updatedTemplate.getContents()).isEqualTo(questions));
    }

    /*    @DisplayName("템플릿 수정 요청을 받아서 기존 템플릿을 업데이트한다.")
    @Test
    void updateTemplateTest2() {
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = UserFixtures.createUser(position);
        var savedUser = userRepository.save(user);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);

        UpdateTemplateRequest request = new UpdateTemplateRequest("변경된 제목", List.of("변경된 질문1", "변경된 질문2"));
        templateService.updateTemplate(savedTemplate.getId(), request, AuthFixture.createAuthContext(savedUser.getId()));

        List<String> contents = tm.execute(new TransactionCallback<List<String>>() {
            @Override
            public List<String> doInTransaction(TransactionStatus status) {
                Template updatedTemplate = templateRepository.findById(savedTemplate.getId()).get();
                return updatedTemplate.getContents();
            }
        });

        assertThat(updatedTemplate)
                .extracting("id", "title", "questions")
                .containsExactly(updatedTemplate.getId(),
                        request.title(),
                        List.of("변경된 질문1", "변경된 질문2"));
    }*/

}
