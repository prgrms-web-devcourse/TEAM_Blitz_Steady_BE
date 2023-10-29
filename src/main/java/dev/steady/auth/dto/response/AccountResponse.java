package dev.steady.auth.dto.response;

import dev.steady.auth.domain.Account;
import dev.steady.auth.domain.Platform;
import dev.steady.user.domain.User;

public record AccountResponse(
        Long id,
        Platform platform,
        String platformId,
        User user
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getPlatform(),
                account.getPlatformId(),
                account.getUser()
        );
    }

}
