package dev.steady.oauth.dto.response;

import dev.steady.auth.dto.response.TokenResponse;

public record LogInResponse(
        Long id,
        boolean isNew,
        TokenResponse token

) {

    public LogInResponse(Long id, boolean isNew) {
        this(id, isNew, null);
    }

    public static LogInResponse of(Long id, boolean isNew){
        return new LogInResponse(id, isNew);
    }

    public static LogInResponse of(Long id, boolean isNew, TokenResponse token){
        return new LogInResponse(id, isNew, token);
    }

}
