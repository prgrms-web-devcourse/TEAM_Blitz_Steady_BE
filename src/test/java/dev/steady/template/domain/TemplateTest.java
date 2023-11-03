package dev.steady.template.domain;

import dev.steady.template.fixture.TemplateFixture;
import dev.steady.user.domain.User;
import dev.steady.user.fixture.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TemplateTest {

    @DisplayName("템플릿의 생성자가 아니면 예외가 발생한다.")
    @Test
    void validateOwnerTest() {
        var position = UserFixtures.createPosition();
        User user = UserFixtures.createFirstUser(position);
        Template template = TemplateFixture.createTemplate(user);

        assertThatThrownBy(() -> template.validateOwner(UserFixtures.createSecondUser(position)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
