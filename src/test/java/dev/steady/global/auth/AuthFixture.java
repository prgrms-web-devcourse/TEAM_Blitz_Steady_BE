package dev.steady.global.auth;

import org.springframework.test.util.ReflectionTestUtils;

public class AuthFixture {

    public static AuthContext createAuthContext(Long userId) {
        var authContext = new AuthContext();
        ReflectionTestUtils.setField(authContext, "userId", userId);
        return authContext;
    }

    public static UserInfo createUserInfo(Long userId) {
        return new UserInfo(userId);
    }

}
