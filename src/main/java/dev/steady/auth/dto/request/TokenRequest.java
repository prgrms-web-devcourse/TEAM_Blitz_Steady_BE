package dev.steady.auth.dto.request;

import dev.steady.auth.domain.Account;

public record TokenRequest(
        Long userId
) {
    public static TokenRequest from(Account account) {
        return new TokenRequest(account.getUser().getId());
    }
}
