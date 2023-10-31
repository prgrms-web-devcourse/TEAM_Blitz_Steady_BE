package dev.steady.user.fixture;

import dev.steady.user.domain.Position;
import dev.steady.user.domain.Stack;
import dev.steady.user.domain.User;

public class UserFixtures {

    public static Position createPosition() {
        return Position.builder()
                .name("백엔드")
                .build();
    }

    public static Position createAnotherPosition() {
        return Position.builder()
                .name("프론트엔드")
                .build();
    }

    public static Stack createStack() {
        return Stack.builder()
                .name("Java")
                .imageUrl("www")
                .build();
    }

    public static Stack createAnotherStack() {
        return Stack.builder()
                .name("JavaScript")
                .imageUrl("www")
                .build();
    }

    public static User createUser(Position position) {
        return User.builder()
                .profileImage("123")
                .nickname("weonest")
                .bio("나에요")
                .position(position)
                .build();
    }

    public static User createAnotherUser(Position position) {
        User user = User.builder()
                .profileImage("1234")
                .nickname("Jun")
                .bio("저에요")
                .position(position)
                .build();
        return user;
    }

}
