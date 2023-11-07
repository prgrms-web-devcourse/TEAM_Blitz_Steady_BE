package dev.steady.global.auth;

import io.jsonwebtoken.Claims;

public record Authentication(
        Long userId
) {

    public static Authentication from(Claims claims) {
        Long userId = Long.valueOf(claims.getSubject());
        return new Authentication(userId);
    }

}

