package dev.steady.template.fixture;

import dev.steady.template.domain.Template;
import dev.steady.template.dto.request.CreateTemplateRequest;
import dev.steady.template.dto.request.UpdateTemplateRequest;
import dev.steady.template.dto.resonse.TemplateDetailResponse;
import dev.steady.template.dto.resonse.TemplateResponse;
import dev.steady.template.dto.resonse.TemplateResponses;
import dev.steady.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public class TemplateFixture {

    public static Template createTemplate(User user) {
        return Template.create(user, "제목", List.of("질문1", "질문2"));
    }

    public static Template createAnotherTemplate(User user) {
        return Template.create(user, "Title", List.of("Q1", "Q2"));
    }

    public static CreateTemplateRequest createTemplateRequest() {
        return new CreateTemplateRequest("Title", List.of("Q1", "Q2"));
    }

    public static TemplateResponses createTemplateResponse() {
        return new TemplateResponses(
                List.of(
                        new TemplateResponse(1L, "제목1", LocalDateTime.of(2023, 12, 7, 12, 00)),
                        new TemplateResponse(2L, "제목2", LocalDateTime.of(2023, 12, 7, 18, 00))
                )
        );
    }

    public static TemplateDetailResponse createDetailTemplateResponse() {
        return new TemplateDetailResponse(
                1L,
                "제목",
                List.of("질문1", "질문2", "질문3", "질문4"),
                LocalDateTime.of(2023, 12, 7, 12, 00),
                LocalDateTime.of(2023, 12, 7, 12, 00));
    }

    public static UpdateTemplateRequest createUpdateTemplateRequest() {
        return new UpdateTemplateRequest("Title", List.of("질문1", "질문2"));
    }

}
