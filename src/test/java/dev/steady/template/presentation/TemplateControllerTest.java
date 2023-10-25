package dev.steady.template.presentation;

import dev.steady.common.config.ControllerTestConfig;
import dev.steady.template.service.TemplateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TemplateController.class)
class TemplateControllerTest extends ControllerTestConfig {

    @MockBean
    private TemplateService templateService;

    @DisplayName("양식 생성 요청을 받아 처리후 204 상태코드를 반환한다.")
    @Test
    void createTemplateTest() {
        // TODO: 2023/10/26 인증인가 개발 이후에 테스트코드 작성 - Beomjun
    }

}
