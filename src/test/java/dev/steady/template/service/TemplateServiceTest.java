package dev.steady.template.service;

import dev.steady.template.domain.repository.QuestionRepository;
import dev.steady.template.domain.repository.TemplateRepository;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.template.dto.request.UpdateTemplateRequest;
import dev.steady.user.domain.repository.PositionRepository;
import dev.steady.user.domain.repository.UserRepository;
import dev.steady.user.fixture.UserFixtures;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static dev.steady.global.auth.AuthFixture.createUserInfo;
import static dev.steady.template.fixture.TemplateFixture.createAnotherTemplate;
import static dev.steady.template.fixture.TemplateFixture.createTemplate;
import static dev.steady.user.fixture.UserFixtures.createFirstUser;
import static dev.steady.user.fixture.UserFixtures.createSecondUser;
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
    private TransactionTemplate transactionTemplate;

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
        var user = userRepository.save(createFirstUser(position));
        var userInfo = createUserInfo(user.getId());
        var questions = List.of("Q1", "Q2");
        var request = new CreateTemplateRequest("Sample Title", questions);

        var templateId = templateService.createTemplate(request, userInfo);

        var savedQuestions = questionRepository.findAll();
        var template = templateRepository.findById(templateId).get();

        assertAll(
                () -> assertThat(template).isNotNull(),
                () -> assertThat(savedQuestions).hasSameSizeAs(questions)
        );

    }

    @DisplayName("사용자의 계정 정보를 받아 템플릿 목록을 조회한다.")
    @Test
    void getTemplatesTest() {
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = createFirstUser(position);
        var savedUser = userRepository.save(user);

        var template1 = createTemplate(savedUser);
        var template2 = createAnotherTemplate(savedUser);
        templateRepository.saveAll(List.of(template1, template2));

        var userInfo = createUserInfo(user.getId());
        var responses = templateService.getTemplates(userInfo);

        assertThat(responses.templates()).hasSize(2)
                .extracting("title")
                .containsExactlyInAnyOrder(template1.getTitle(), template2.getTitle());
    }

    @DisplayName("템플릿 식별자를 통해 템플릿을 상세조회한다.")
    @Test
    void getDetailTemplateTest() {
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = createFirstUser(position);
        var savedUser = userRepository.save(user);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);

        var userInfo = createUserInfo(user.getId());
        var response = templateService.getDetailTemplate(userInfo, savedTemplate.getId());

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
        var user = createFirstUser(position);
        var savedUser = userRepository.save(user);
        var anotherUser = createSecondUser(position);
        userRepository.save(anotherUser);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);

        var userInfo = createUserInfo(anotherUser.getId());

        assertThatThrownBy(() -> templateService.getDetailTemplate(userInfo, savedTemplate.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("템플릿 수정 요청을 받아서 기존 템플릿을 업데이트한다.")
    @Test
    void updateTemplateTest() {
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = createFirstUser(position);
        var savedUser = userRepository.save(user);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);

        var questions = List.of("변경된 질문1", "변경된 질문2", "변경된 질문3");
        var title = "변경된 제목";
        var request = new UpdateTemplateRequest(title, questions);
        templateService.updateTemplate(savedTemplate.getId(), request, createUserInfo(user.getId()));

        var updatedTemplate = transactionTemplate.execute(status -> {
            var findTemplate = templateRepository.findById(savedTemplate.getId()).get();
            findTemplate.getContents();
            return findTemplate;
        });
        assertAll(
                () -> assertThat(updatedTemplate.getTitle()).isEqualTo(title),
                () -> assertThat(updatedTemplate.getContents()).isEqualTo(questions));
    }

    @DisplayName("템플릿 작성자는 템플릿을 삭제할 수 있다.")
    @Test
    void deleteTemplateTest() {
        //given
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = createFirstUser(position);
        var savedUser = userRepository.save(user);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);

        //when
        templateService.deleteTemplate(createUserInfo(savedUser.getId()), savedTemplate.getId());

        //then
        assertThatThrownBy(() -> templateRepository.getById(savedTemplate.getId()))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    @DisplayName("템플릿 작성자가 아니면 템플릿을 삭제할 수 없다.")
    @Test
    void deleteTemplateFailTest() {
        //given
        var position = positionRepository.save(UserFixtures.createPosition());
        var user = createFirstUser(position);
        var savedUser = userRepository.save(user);
        var position2 = positionRepository.save(UserFixtures.createPosition());
        var user2 = createSecondUser(position2);
        var savedUser2 = userRepository.save(user2);

        var template = createTemplate(savedUser);
        var savedTemplate = templateRepository.save(template);
        var userInfo = createUserInfo(savedUser2.getId());
        //when
        //then
        assertThatThrownBy(() -> templateService.deleteTemplate(userInfo, savedTemplate.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
