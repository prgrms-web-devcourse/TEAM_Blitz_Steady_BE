package dev.steady.auth.oauth.dto.response;

import dev.steady.auth.dto.response.TokenResponse;

public record LogInResponse(
        Long id,
        boolean isNew,
        TokenResponse token

) {

    public LogInResponse(Long id, boolean isNew) {
        this(id, isNew, null);
    }

    public static LogInResponse forUserNotExist(Long id) {
        return new LogInResponse(id, true);
    }

    public static LogInResponse forUserExist(Long id, TokenResponse token) {
        return new LogInResponse(id, false, token);
    }

}
