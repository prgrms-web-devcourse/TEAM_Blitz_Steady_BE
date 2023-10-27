package dev.steady.user.fixture;

import dev.steady.user.domain.Platform;
import dev.steady.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public class UserFixtures {

    public static User createUser() {
        User user = User.builder()
                .platform_id("1")
                .platform(Platform.KAKAO)
                .profileImage("123")
                .nickname("weonest")
                .bio("나에요")
                .isDeleted(null)
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);
        return user;
    }

    public static User createAnotherUser() {
        User user = User.builder()
                .platform_id("2")
                .platform(Platform.KAKAO)
                .profileImage("1234")
                .nickname("Jun")
                .bio("저에요")
                .isDeleted(null)
                .build();
        ReflectionTestUtils.setField(user, "id", 2L);
        return user;
    }

}
