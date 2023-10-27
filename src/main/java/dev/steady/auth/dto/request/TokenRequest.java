package dev.steady.auth.dto.request;

import dev.steady.auth.dto.response.AccountResponse;

public record TokenRequest(
        Long userId
) {
    public static TokenRequest from(AccountResponse accountResponse) {
        return new TokenRequest(accountResponse.user().getId());
    }
}
