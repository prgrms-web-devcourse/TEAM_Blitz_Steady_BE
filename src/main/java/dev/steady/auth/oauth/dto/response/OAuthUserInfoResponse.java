package dev.steady.auth.oauth.dto.response;

import dev.steady.auth.domain.Account;
import dev.steady.auth.oauth.domain.Platform;

public interface OAuthUserInfoResponse {

    static Account toEntity(OAuthUserInfoResponse response) {
        return Account.builder()
                .platform(response.getPlatform())
                .platformId(response.getPlatformId())
                .build();
    }

    Platform getPlatform();

    String getPlatformId();

}
