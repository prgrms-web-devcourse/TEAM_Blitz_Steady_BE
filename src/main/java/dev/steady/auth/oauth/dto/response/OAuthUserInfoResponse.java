package dev.steady.auth.oauth.dto.response;

import dev.steady.auth.domain.Account;
import dev.steady.auth.domain.Platform;

public interface OAuthUserInfoResponse {

    default Account toAccountEntity() {
        return new Account(this.getPlatform(), this.getPlatformId());
    }

    Platform getPlatform();

    String getPlatformId();

}
