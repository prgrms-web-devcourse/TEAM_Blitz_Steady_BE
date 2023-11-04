package dev.steady.global.auth;

public record UserInfo(
        Long userId
) {

    public static UserInfo from(AuthContext authContext) {
        return new UserInfo(authContext.getUserId());
    }

    public boolean isAnonymous() {
        return this.userId == null;
    }
    
}
