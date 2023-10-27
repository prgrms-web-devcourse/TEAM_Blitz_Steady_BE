package dev.steady.template.fixture;

import dev.steady.template.domain.Template;
import dev.steady.user.domain.User;

import java.util.List;

public class TemplateFixture {

    public static Template createTemplate(User user) {
        return Template.create(user, "제목", List.of("질문1", "질문2"));
    }

    public static Template createAnotherTemplate(User user) {
        return Template.create(user, "Title", List.of("Q1", "Q2"));
    }

}
